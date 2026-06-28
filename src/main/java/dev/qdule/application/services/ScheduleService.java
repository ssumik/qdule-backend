package dev.qdule.application.services;

import java.time.ZonedDateTime;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.mapper.ScheduleMapper;
import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.model.ScheduleStatus;
import dev.qdule.domain.model.Treatment;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.domain.repository.ScheduleRepository;
import dev.qdule.domain.repository.TreatmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleService {
        private ScheduleRepository scheduleRepository;
        private TreatmentRepository treatmentRepository;
        private ClientRepository clientRepository;

        @Inject
        public ScheduleService(ScheduleRepository scheduleRepository, TreatmentRepository treatmentRepository,
                        ClientRepository clientRepository) {
                this.scheduleRepository = scheduleRepository;
                this.treatmentRepository = treatmentRepository;
                this.clientRepository = clientRepository;
        }

        public PageResponse<ScheduleResponse> getSchedules(
                        int page,
                        int size,
                        ZonedDateTime start,
                        ZonedDateTime end,
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

                // TODO: VALIDAR SE O SCHEDULE ESTA SENDO EM UM DIA LIVRE
                // TODO: VALIDAR SE JA EXISTE ALGO PARA ESTE HORARIO QUE SERIA CONFLITANTE

                Schedule schedule = new Schedule(
                                treatment,
                                client,
                                scheduleRequest.getStartDateTime(),
                                scheduleRequest.getEndDateTime(),
                                scheduleRequest.getReason(),
                                scheduleRequest.getStatus());

                var savedSchedule = scheduleRepository.save(schedule);

                return ScheduleMapper.toResponse(savedSchedule);
        }

        @Transactional
        public ScheduleResponse updateSchedule(Long id, ScheduleUpdateRequest scheduleRequest) {
                Schedule schedule = scheduleRepository.findById(id)
                                .orElseThrow(() -> new ScheduleNotFoundException(id));

                schedule.setStartDateTime(scheduleRequest.getStartDateTime());
                schedule.setEndDateTime(scheduleRequest.getEndDateTime());
                schedule.setReason(scheduleRequest.getReason());
                schedule.setStatus(scheduleRequest.getStatus());

                var savedSchedule = scheduleRepository.save(schedule);
                return ScheduleMapper.toResponse(savedSchedule);
        }

        @Transactional
        public void deleteScheduleById(Long id) {
                scheduleRepository.removeById(id);
        }
}
