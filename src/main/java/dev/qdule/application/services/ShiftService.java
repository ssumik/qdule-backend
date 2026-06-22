package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ShiftCreateRequest;
import dev.qdule.application.dto.requests.ShiftUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ShiftResponse;
import dev.qdule.application.mapper.ShiftMapper;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.repository.ShiftRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ShiftService {
    private ShiftRepository shiftRepository;

    @Inject
    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public PageResponse<ShiftResponse> getShifts(int page, int size) {
        var shiftList = shiftRepository.findAll(page, size);

        PageResponse<ShiftResponse> response = new PageResponse<>();

        response.setContent(
                shiftList.getContent()
                        .stream()
                        .map(ShiftMapper::toResponse)
                        .toList());

        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(shiftList.getTotalElements());
        response.setTotalPages(shiftList.getTotalPages());

        return response;
    }

    public ShiftResponse getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        return ShiftMapper.toResponse(shift);
    }

    @Transactional
    public ShiftResponse createShift(ShiftCreateRequest shiftRequest) {
        Shift shift = new Shift(
                shiftRequest.getName(),
                shiftRequest.getStartTime(),
                shiftRequest.getEndTime(),
                shiftRequest.getRestTimeBetweenAppointments(),
                shiftRequest.getBreakStartTime(),
                shiftRequest.getBreakEndTime());

        var savedShift = shiftRepository.save(shift);

        return ShiftMapper.toResponse(savedShift);
    }

    @Transactional
    public ShiftResponse updateShift(Long id, ShiftUpdateRequest shiftRequest) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        shift.setName(shiftRequest.getName());
        shift.setStartTime(shiftRequest.getStartTime());
        shift.setEndTime(shiftRequest.getEndTime());
        shift.setRestTimeBetweenAppointments(shiftRequest.getRestTimeBetweenAppointments());
        shift.setBreakStartTime(shiftRequest.getBreakStartTime());
        shift.setBreakEndTime(shiftRequest.getBreakEndTime());

        var savedShift = shiftRepository.save(shift);
        return ShiftMapper.toResponse(savedShift);
    }

    @Transactional
    public void deleteShiftById(Long id) {
        shiftRepository.removeById(id);
    }
}
