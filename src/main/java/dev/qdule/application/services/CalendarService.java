package dev.qdule.application.services;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.qdule.application.dto.responses.CalendarListResponse;
import dev.qdule.application.dto.responses.CalendarResponse;
import dev.qdule.application.exception.TreatmentDisabledException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.mapper.CalendarListMapper;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleException;
import dev.qdule.domain.model.ScheduleExceptionBreak;
import dev.qdule.domain.model.ScheduleStatus;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftBreak;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.model.TreatmentStatus;
import dev.qdule.domain.repository.ScheduleExceptionRepository;
import dev.qdule.domain.repository.ScheduleRepository;
import dev.qdule.domain.repository.ShiftRepository;
import dev.qdule.domain.repository.TreatmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CalendarService {
        private ScheduleRepository scheduleRepository;
        private ScheduleExceptionRepository scheduleExceptionRepository;
        private TreatmentRepository treatmentRepository;
        private ShiftRepository shiftRepository;

        @Inject
        public CalendarService(
                        ScheduleRepository scheduleRepository,
                        ScheduleExceptionRepository scheduleExceptionRepository,
                        TreatmentRepository treatmentRepository,
                        ShiftRepository shiftRepository) {
                this.scheduleRepository = scheduleRepository;
                this.scheduleExceptionRepository = scheduleExceptionRepository;
                this.treatmentRepository = treatmentRepository;
                this.shiftRepository = shiftRepository;
        }

        public CalendarListResponse availableSchedule(
                        Long treatmentId,
                        int year,
                        int month) {

                LocalDate startDate = getStartDate(year, month);
                LocalDate endDate = getEndDate(year, month);

                var treatment = treatmentRepository.findById(treatmentId)
                                .orElseThrow(() -> new TreatmentNotFoundException(treatmentId));

                if (treatment.getStatus() == TreatmentStatus.INACTIVE) {
                        throw new TreatmentDisabledException(treatmentId);
                }

                List<CalendarResponse> response = new ArrayList<>();
                Map<DayOfWeek, Shift> shiftsByDay = loadEnabledShiftsByDay(startDate, endDate);
                Map<LocalDate, List<Schedule>> schedulesByDate = loadBlockingSchedulesByDate(startDate, endDate);
                Map<LocalDate, List<ScheduleException>> exceptionsByDate = loadBlockingExceptionsByDate(startDate,
                                endDate);

                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                        var shift = shiftsByDay.get(date.getDayOfWeek());

                        if (shift == null) {
                                continue;
                        }

                        List<Schedule> schedules = schedulesByDate.getOrDefault(date, List.of());
                        List<ScheduleException> exceptions = exceptionsByDate.getOrDefault(date, List.of());
                        List<LocalTime> slots = generateCandidateSlots(date, treatment, shift, schedules, exceptions);
                        response.add(buildResponse(treatmentId, date, slots));
                }

                return CalendarListMapper.toResponse(response);
        }

        public CalendarListResponse scheduledTreatment(
                        int year,
                        int month) {
                LocalDate startDate = getStartDate(year, month);
                LocalDate endDate = getEndDate(year, month);

                var responseList = scheduleRepository.findByStatuses(
                                startDate.atStartOfDay(),
                                endDate.plusDays(1).atStartOfDay(),
                                List.of(
                                                ScheduleStatus.SCHEDULED,
                                                ScheduleStatus.RESCHEDULED))
                                .stream()
                                .collect(Collectors.groupingBy(schedule -> new ScheduledTreatmentGroup(
                                                schedule.getStartDateTime().toLocalDate(),
                                                schedule.getTreatment().getId())))
                                .entrySet()
                                .stream()
                                .map(entry -> buildResponse(
                                                entry.getKey().treatmentId(),
                                                entry.getKey().date(),
                                                entry.getValue()
                                                                .stream()
                                                                .map(schedule -> schedule.getStartDateTime()
                                                                                .toLocalTime())
                                                                .toList()))
                                .sorted(Comparator.comparing(CalendarResponse::getDate)
                                                .thenComparing(CalendarResponse::getTreatmentId))
                                .toList();

                return CalendarListMapper.toResponse(responseList);
        }

        private record ScheduledTreatmentGroup(LocalDate date, Long treatmentId) {
        }

        private LocalDate getStartDate(int year, int month) {
                return YearMonth.of(year, month).atDay(1);
        }

        private LocalDate getEndDate(int year, int month) {
                return YearMonth.of(year, month).atEndOfMonth();
        }

        private CalendarResponse buildResponse(Long treatmentId, LocalDate date, List<LocalTime> slots) {
                List<LocalTime> hours = slots.stream()
                                .sorted(Comparator.naturalOrder())
                                .toList();

                return new CalendarResponse(treatmentId, date.toString(), hours);
        }

        private Map<DayOfWeek, Shift> loadEnabledShiftsByDay(LocalDate startDate, LocalDate endDate) {
                Set<DayOfWeek> days = new HashSet<>();

                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                        days.add(date.getDayOfWeek());
                }

                return shiftRepository.findEnabledByDays(days)
                                .stream()
                                .collect(Collectors.toMap(Shift::getDayOfWeek, Function.identity()));
        }

        private Map<LocalDate, List<Schedule>> loadBlockingSchedulesByDate(LocalDate startDate, LocalDate endDate) {
                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = endDate.plusDays(1).atStartOfDay();

                Map<LocalDate, List<Schedule>> schedulesByDate = new HashMap<>();

                scheduleRepository.findBlockingSchedules(
                                start,
                                end,
                                List.of(
                                                ScheduleStatus.SCHEDULED,
                                                ScheduleStatus.RESCHEDULED,
                                                ScheduleStatus.PENDING))
                                .forEach(schedule -> addScheduleToOverlappingDates(
                                                schedulesByDate,
                                                schedule,
                                                startDate,
                                                endDate));

                return schedulesByDate;
        }

        private Map<LocalDate, List<ScheduleException>> loadBlockingExceptionsByDate(LocalDate startDate,
                        LocalDate endDate) {
                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = endDate.plusDays(1).atStartOfDay();

                Map<LocalDate, List<ScheduleException>> exceptionsByDate = new HashMap<>();

                scheduleExceptionRepository.findBlockingExceptions(start, end)
                                .forEach(scheduleException -> addExceptionToOverlappingDates(
                                                exceptionsByDate,
                                                scheduleException,
                                                startDate,
                                                endDate));

                return exceptionsByDate;
        }

        private void addScheduleToOverlappingDates(
                        Map<LocalDate, List<Schedule>> schedulesByDate,
                        Schedule schedule,
                        LocalDate startDate,
                        LocalDate endDate) {

                LocalDate firstDate = schedule.getStartDateTime().toLocalDate().isBefore(startDate)
                                ? startDate
                                : schedule.getStartDateTime().toLocalDate();
                LocalDate lastDate = schedule.getEndDateTime().minusNanos(1).toLocalDate().isAfter(endDate)
                                ? endDate
                                : schedule.getEndDateTime().minusNanos(1).toLocalDate();

                for (LocalDate date = firstDate; !date.isAfter(lastDate); date = date.plusDays(1)) {
                        schedulesByDate.computeIfAbsent(date, ignored -> new ArrayList<>())
                                        .add(schedule);
                }
        }

        private void addExceptionToOverlappingDates(
                        Map<LocalDate, List<ScheduleException>> exceptionsByDate,
                        ScheduleException scheduleException,
                        LocalDate startDate,
                        LocalDate endDate) {

                LocalDate firstDate = scheduleException.getStartDateTime().toLocalDate().isBefore(startDate)
                                ? startDate
                                : scheduleException.getStartDateTime().toLocalDate();
                LocalDate lastDate = scheduleException.getEndDateTime().minusNanos(1).toLocalDate().isAfter(endDate)
                                ? endDate
                                : scheduleException.getEndDateTime().minusNanos(1).toLocalDate();

                for (LocalDate date = firstDate; !date.isAfter(lastDate); date = date.plusDays(1)) {
                        exceptionsByDate.computeIfAbsent(date, ignored -> new ArrayList<>())
                                        .add(scheduleException);
                }
        }

        private List<LocalTime> generateCandidateSlots(
                        LocalDate date,
                        Treatment treatment,
                        Shift shift,
                        List<Schedule> schedules,
                        List<ScheduleException> exceptions) {
                Duration treatmentDuration = treatment.getDuration();
                Duration restTime = shift.getRestTimeBetweenAppointments() == null
                                ? Duration.ZERO
                                : shift.getRestTimeBetweenAppointments();
                Duration slotStep = treatmentDuration.plus(restTime);

                List<LocalTime> slots = new ArrayList<>();

                LocalTime cursor = shift.getStartTime();
                while (!cursor.plus(treatmentDuration).isAfter(shift.getEndTime())) {
                        LocalTime slotStart = cursor;
                        LocalTime slotEnd = cursor.plus(treatmentDuration);

                        if (!overlapsBreak(slotStart, slotEnd, shift.getBreaks())
                                        && !overlapsSchedule(date, slotStart, slotEnd, schedules)
                                        && !overlapsScheduleException(date, slotStart, slotEnd, exceptions)) {
                                slots.add(slotStart);
                        }

                        cursor = cursor.plus(slotStep);
                }

                return slots;
        }

        private boolean overlapsBreak(LocalTime slotStart, LocalTime slotEnd, List<ShiftBreak> breaks) {
                return breaks.stream()
                                .anyMatch(shiftBreak -> overlaps(
                                                slotStart,
                                                slotEnd,
                                                shiftBreak.getStartTime(),
                                                shiftBreak.getEndTime()));
        }

        private boolean overlapsSchedule(LocalDate date, LocalTime slotStart, LocalTime slotEnd,
                        List<Schedule> schedules) {
                LocalDateTime slotStartDateTime = date.atTime(slotStart);
                LocalDateTime slotEndDateTime = date.atTime(slotEnd);

                return schedules.stream()
                                .anyMatch(schedule -> slotStartDateTime.isBefore(schedule.getEndDateTime())
                                                && slotEndDateTime.isAfter(schedule.getStartDateTime()));
        }

        private boolean overlapsScheduleException(LocalDate date, LocalTime slotStart, LocalTime slotEnd,
                        List<ScheduleException> exceptions) {
                LocalDateTime slotStartDateTime = date.atTime(slotStart);
                LocalDateTime slotEndDateTime = date.atTime(slotEnd);

                return exceptions.stream()
                                .anyMatch(scheduleException -> overlapsScheduleException(
                                                slotStartDateTime,
                                                slotEndDateTime,
                                                scheduleException));
        }

        private boolean overlapsScheduleException(LocalDateTime slotStartDateTime, LocalDateTime slotEndDateTime,
                        ScheduleException scheduleException) {
                if (scheduleException.getBreaks().isEmpty()) {
                        return slotStartDateTime.isBefore(scheduleException.getEndDateTime())
                                        && slotEndDateTime.isAfter(scheduleException.getStartDateTime());
                }

                return scheduleException.getBreaks().stream()
                                .anyMatch(scheduleExceptionBreak -> overlapsScheduleExceptionBreak(
                                                slotStartDateTime,
                                                slotEndDateTime,
                                                scheduleExceptionBreak));
        }

        private boolean overlapsScheduleExceptionBreak(LocalDateTime slotStartDateTime, LocalDateTime slotEndDateTime,
                        ScheduleExceptionBreak scheduleExceptionBreak) {
                return slotStartDateTime.isBefore(scheduleExceptionBreak.getEndDateTime())
                                && slotEndDateTime.isAfter(scheduleExceptionBreak.getStartDateTime());
        }

        private boolean overlaps(LocalTime firstStart, LocalTime firstEnd, LocalTime secondStart,
                        LocalTime secondEnd) {
                return firstStart.isBefore(secondEnd) && firstEnd.isAfter(secondStart);
        }
}
