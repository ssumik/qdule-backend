package dev.qdule.application.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.application.exception.ShiftDisabledException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.mapper.ScheduleMapper;
import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftStatus;
import dev.qdule.domain.model.Treatment;
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
