package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.mapper.ScheduleMapper;
import dev.qdule.domain.model.Schedule;
import dev.qdule.domain.repository.ScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public PageResponse<ScheduleResponse> getSchedules(int page, int size) {
        var scheduleList = scheduleRepository.findAll(page, size);

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
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return ScheduleMapper.toResponse(schedule);
    }

    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest scheduleRequest) {
        Schedule schedule = new Schedule(
                scheduleRequest.getTreatmentId(),
                scheduleRequest.getClientId(),
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
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setTreatmentId(scheduleRequest.getTreatmentId());
        schedule.setClientId(scheduleRequest.getClientId());
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
