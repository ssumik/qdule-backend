package dev.qdule.application.services;

import dev.qdule.application.dto.requests.WorkScheduleCreateRequest;
import dev.qdule.application.dto.requests.WorkScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.WorkScheduleResponse;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.exception.WorkScheduleNotFoundException;
import dev.qdule.application.mapper.WorkScheduleMapper;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.WorkSchedule;
import dev.qdule.domain.repository.ShiftRepository;
import dev.qdule.domain.repository.WorkScheduleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WorkScheduleService {
    private WorkScheduleRepository workScheduleRepository;
    private ShiftRepository shiftRepository;

    @Inject
    public WorkScheduleService(WorkScheduleRepository workScheduleRepository, ShiftRepository shiftRepository) {
        this.workScheduleRepository = workScheduleRepository;
        this.shiftRepository = shiftRepository;
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
        Shift shift = shiftRepository.findById(workScheduleRequest.getShiftId())
                .orElseThrow(() -> new ShiftNotFoundException(workScheduleRequest.getShiftId()));
        WorkSchedule workSchedule = new WorkSchedule(
                shift,
                workScheduleRequest.getDayOfWeek());

        var savedWorkSchedule = workScheduleRepository.save(workSchedule);

        return WorkScheduleMapper.toResponse(savedWorkSchedule);
    }

    @Transactional
    public WorkScheduleResponse updateWorkSchedule(Long id, WorkScheduleUpdateRequest workScheduleRequest) {
        Shift shift = shiftRepository.findById(workScheduleRequest.getShiftId())
                .orElseThrow(() -> new ShiftNotFoundException(workScheduleRequest.getShiftId()));

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new WorkScheduleNotFoundException(id));

        workSchedule.setShift(shift);
        workSchedule.setDayOfWeek(workScheduleRequest.getDayOfWeek());

        var savedWorkSchedule = workScheduleRepository.save(workSchedule);
        return WorkScheduleMapper.toResponse(savedWorkSchedule);
    }

    @Transactional
    public void deleteWorkScheduleById(Long id) {
        workScheduleRepository.removeById(id);
    }
}
