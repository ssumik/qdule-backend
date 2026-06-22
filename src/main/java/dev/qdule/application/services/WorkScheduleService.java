package dev.qdule.application.services;

import dev.qdule.application.dto.requests.WorkScheduleCreateRequest;
import dev.qdule.application.dto.requests.WorkScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.WorkScheduleResponse;
import dev.qdule.application.mapper.WorkScheduleMapper;
import dev.qdule.domain.model.WorkSchedule;
import dev.qdule.domain.repository.WorkScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WorkScheduleService {
    private WorkScheduleRepository workScheduleRepository;

    @Inject
    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    public PageResponse<WorkScheduleResponse> getWorkSchedules(int page, int size) {
        var workScheduleList = workScheduleRepository.findAll(page, size);

        PageResponse<WorkScheduleResponse> response = new PageResponse<>();

        response.setContent(
                workScheduleList.getContent()
                        .stream()
                        .map(WorkScheduleMapper::toResponse)
                        .toList());

        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(workScheduleList.getTotalElements());
        response.setTotalPages(workScheduleList.getTotalPages());

        return response;
    }

    public WorkScheduleResponse getWorkScheduleById(Long id) {
        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found"));
        return WorkScheduleMapper.toResponse(workSchedule);
    }

    @Transactional
    public WorkScheduleResponse createWorkSchedule(WorkScheduleCreateRequest workScheduleRequest) {
        WorkSchedule workSchedule = new WorkSchedule(
                workScheduleRequest.getShift(),
                workScheduleRequest.getDayOfWeek());

        var savedWorkSchedule = workScheduleRepository.save(workSchedule);

        return WorkScheduleMapper.toResponse(savedWorkSchedule);
    }

    @Transactional
    public WorkScheduleResponse updateWorkSchedule(Long id, WorkScheduleUpdateRequest workScheduleRequest) {
        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkSchedule not found"));

        workSchedule.setShift(workScheduleRequest.getShift());
        workSchedule.setDayOfWeek(workScheduleRequest.getDayOfWeek());

        var savedWorkSchedule = workScheduleRepository.save(workSchedule);
        return WorkScheduleMapper.toResponse(savedWorkSchedule);
    }

    @Transactional
    public void deleteWorkScheduleById(Long id) {
        workScheduleRepository.removeById(id);
    }
}
