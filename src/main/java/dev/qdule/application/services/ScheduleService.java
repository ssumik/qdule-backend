package dev.qdule.application.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.AvaliableScheduleResponse;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.application.exception.ShiftDisabledException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.exception.TreatmentDisabledException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.mapper.ScheduleMapper;
import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftBreak;
import dev.qdule.domain.model.ShiftStatus;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.model.TreatmentStatus;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.domain.repository.ScheduleRepository;
import dev.qdule.domain.repository.ShiftRepository;
import dev.qdule.domain.repository.TreatmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleService {
        private ScheduleRepository scheduleRepository;
        private TreatmentRepository treatmentRepository;
        private ClientRepository clientRepository;
        private ShiftRepository shiftRepository;

        @Inject
        public ScheduleService(ScheduleRepository scheduleRepository, TreatmentRepository treatmentRepository,
                        ClientRepository clientRepository, ShiftRepository shiftRepository) {
                this.scheduleRepository = scheduleRepository;
                this.treatmentRepository = treatmentRepository;
                this.clientRepository = clientRepository;
                this.shiftRepository = shiftRepository;
        }

        public PageResponse<ScheduleResponse> getSchedules(
                        int page,
                        int size,
                        LocalDateTime start,
                        LocalDateTime end,
                        ScheduleStatus status) {
                var scheduleList = scheduleRepository.findAll(
                                page,
                                size,
                                start,
                                end,
                                status);

                PageResponse<ScheduleResponse> response = new PageResponse<>();

                response.setContent(
                                scheduleList.getContent()
                                                .stream()
                                                .map(ScheduleMapper::toResponse)
                                                .toList());

                response.setPage(page);
                response.setSize(size);
                response.setTotalElements(scheduleList.getTotalElements());
                response.setTotalPages(scheduleList.getTotalPages());

                return response;
        }

        public ScheduleResponse getScheduleById(Long id) {
                Schedule schedule = scheduleRepository.findById(id)
                                .orElseThrow(() -> new ScheduleNotFoundException(id));
                return ScheduleMapper.toResponse(schedule);
        }

        public List<AvaliableScheduleResponse> availableSchedule(
                        Long treatmentId,
                        LocalDate startDate,
                        LocalDate endDate) {

                var treatment = treatmentRepository.findById(treatmentId)
                                .orElseThrow(() -> new TreatmentNotFoundException(treatmentId));

                if (treatment.getStatus() == TreatmentStatus.INACTIVE) {
                        throw new TreatmentDisabledException(treatmentId);
                }

                List<AvaliableScheduleResponse> response = new ArrayList<>();
                Map<DayOfWeek, Shift> shiftsByDay = loadEnabledShiftsByDay(startDate, endDate);
                Map<LocalDate, List<Schedule>> schedulesByDate = loadBlockingSchedulesByDate(startDate, endDate);

                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                        var shift = shiftsByDay.get(date.getDayOfWeek());

                        if (shift == null) {
                                continue;
                        }

                        List<Schedule> schedules = schedulesByDate.getOrDefault(date, List.of());
                        List<LocalTime> slots = generateCandidateSlots(date, treatment, shift, schedules);
                        response.add(buildResponse(treatmentId, date, slots));
                }

                return response;
        }

        private AvaliableScheduleResponse buildResponse(Long treatmentId, LocalDate date, List<LocalTime> slots) {
                List<LocalTime> hours = slots.stream()
                                .sorted(Comparator.naturalOrder())
                                .toList();

                return new AvaliableScheduleResponse(treatmentId, date.toString(), hours);
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

        private List<LocalTime> generateCandidateSlots(
                        LocalDate date,
                        Treatment treatment,
                        Shift shift,
                        List<Schedule> schedules) {
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
                                        && !overlapsSchedule(date, slotStart, slotEnd, schedules)) {
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
                LocalDateTime zonedSlotStart = date.atTime(slotStart);
                LocalDateTime zonedSlotEnd = date.atTime(slotEnd);

                return schedules.stream()
                                .anyMatch(schedule -> zonedSlotStart.isBefore(schedule.getEndDateTime())
                                                && zonedSlotEnd.isAfter(schedule.getStartDateTime()));
        }

        private boolean overlaps(LocalTime firstStart, LocalTime firstEnd, LocalTime secondStart,
                        LocalTime secondEnd) {
                return firstStart.isBefore(secondEnd) && firstEnd.isAfter(secondStart);
        }

        @Transactional
        public ScheduleResponse createSchedule(ScheduleCreateRequest scheduleRequest) {
                Treatment treatment = treatmentRepository.findById(scheduleRequest.getTreatmentId())
                                .orElseThrow(() -> new TreatmentNotFoundException(scheduleRequest.getTreatmentId()));

                Client client = clientRepository.findById(scheduleRequest.getClientId())
                                .orElseThrow(() -> new ClientNotFoundException(scheduleRequest.getClientId()));

                Shift shift = shiftRepository.findByDay(scheduleRequest.getStartDateTime().getDayOfWeek())
                                .orElseThrow(() -> new ShiftNotFoundException(
                                                "Shift not configurated for day "
                                                                + scheduleRequest.getStartDateTime().getDayOfWeek()));

                Schedule schedule = new Schedule(
                                treatment,
                                client,
                                scheduleRequest.getStartDateTime(),
                                scheduleRequest.getEndDateTime(),
                                scheduleRequest.getReason(),
                                scheduleRequest.getStatus());

                validateScheduleDate(schedule);
                validateShift(shift, schedule);
                validateScheduledPeriod(schedule);

                var savedSchedule = scheduleRepository.save(schedule);

                return ScheduleMapper.toResponse(savedSchedule);
        }

        private void validateScheduleDate(Schedule schedule) {
                if (schedule.getStartDateTime().isBefore(schedule.getStartDateTime())) {
                        throw new ConflictException(
                                        "The schedule start date cannot be in the past");
                }

                if (!schedule.getEndDateTime().isAfter(schedule.getStartDateTime())) {
                        throw new ConflictException(
                                        "The schedule end date must be after the start date");
                }

                ZonedDateTime limit = ZonedDateTime.now(
                                ZoneId.of("America/Sao_Paulo"))
                                .plusMonths(3);

                if (schedule.getStartDateTime().isAfter(limit.toLocalDateTime())) {
                        throw new ConflictException(
                                        "The schedule start date cannot be more than 3 months in the future");
                }
        }

        private void validateShift(Shift shift, Schedule schedule) {
                if (shift.getStatus() == ShiftStatus.DISABLED) {
                        throw new ShiftDisabledException();
                }

                LocalDateTime scheduleStart = schedule.getStartDateTime();
                LocalDateTime scheduleEnd = schedule.getEndDateTime();

                boolean startsInside = !scheduleStart.toLocalTime().isBefore(shift.getStartTime());
                boolean endsInside = !scheduleEnd.toLocalTime().isAfter(shift.getEndTime());

                if (!startsInside || !endsInside) {
                        throw new ConflictException(
                                        "The schedule time must be within the shift hours: "
                                                        + shift.getStartTime()
                                                        + " ~ "
                                                        + shift.getEndTime());
                }

                boolean conflictsWithBreak = shift.getBreaks()
                                .stream()
                                .anyMatch(shiftBreak -> scheduleStart.toLocalTime().isBefore(shiftBreak.getEndTime())
                                                && scheduleEnd.toLocalTime().isAfter(shiftBreak.getStartTime()));

                if (conflictsWithBreak) {
                        throw new ConflictException("The schedule time conflicts with a shift break");
                }
        }

        private void validateScheduledPeriod(Schedule schedule) {
                if (!isPlannedSchedule(schedule.getStatus())) {
                        return;
                }

                var scheduledList = scheduleRepository.findAll(1, 1, schedule.getStartDateTime(),
                                schedule.getEndDateTime(), ScheduleStatus.SCHEDULED);

                var rescheduledList = scheduleRepository.findAll(1, 1, schedule.getStartDateTime(),
                                schedule.getEndDateTime(), ScheduleStatus.RESCHEDULED);

                if (scheduledList.getContent().size() != 0 || rescheduledList.getContent().size() != 0) {
                        throw new ConflictException("There is already a planned schedule for this period");
                }
        }

        private boolean isPlannedSchedule(ScheduleStatus status) {
                return status == ScheduleStatus.SCHEDULED || status == ScheduleStatus.RESCHEDULED;
        }

        @Transactional
        public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest scheduleRequest) {
                Schedule schedule = scheduleRepository.findById(id)
                                .orElseThrow(() -> new ScheduleNotFoundException(id));

                Shift shift = shiftRepository.findByDay(scheduleRequest.getStartDateTime().getDayOfWeek())
                                .orElseThrow(() -> new ShiftNotFoundException(
                                                "Shift not configurated for day "
                                                                + scheduleRequest.getStartDateTime().getDayOfWeek()));

                schedule.setStartDateTime(scheduleRequest.getStartDateTime());
                schedule.setEndDateTime(scheduleRequest.getEndDateTime());
                schedule.setReason(scheduleRequest.getReason());
                schedule.setStatus(scheduleRequest.getStatus());

                validateScheduleDate(schedule);
                validateShift(shift, schedule);
                validateScheduledPeriod(schedule);

                var savedSchedule = scheduleRepository.save(schedule);
                return ScheduleMapper.toResponse(savedSchedule);
        }

        @Transactional
        public void deleteScheduleById(Long id) {
                scheduleRepository.removeById(id);
        }
}
