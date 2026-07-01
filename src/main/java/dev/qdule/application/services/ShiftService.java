package dev.qdule.application.services;

import java.util.Comparator;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;

import dev.qdule.application.dto.requests.ShiftCreateRequest;
import dev.qdule.application.dto.requests.ShiftUpdateRequest;
import dev.qdule.application.dto.responses.ShiftListResponse;
import dev.qdule.application.dto.responses.ShiftResponse;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.mapper.ShiftBreakMapper;
import dev.qdule.application.mapper.ShiftListMapper;
import dev.qdule.application.mapper.ShiftMapper;
import dev.qdule.domain.model.Shift;
import dev.qdule.domain.model.ShiftBreak;
import dev.qdule.domain.model.ShiftStatus;
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

    public ShiftListResponse getShifts(ShiftStatus status, List<DayOfWeek> days) {
        var shiftList = shiftRepository.findAll(status, days);

        ShiftListResponse response = ShiftListMapper.toResponse(shiftList);

        return response;
    }

    public ShiftResponse getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new ShiftNotFoundException(id));
        return ShiftMapper.toResponse(shift);
    }

    @Transactional
    public ShiftResponse createShift(ShiftCreateRequest shiftRequest) {
        shiftRepository.findByDay(shiftRequest.getDayOfWeek())
                .ifPresent(shift -> {
                    throw new ConflictException("There is already a shift set for this day");
                });

        var breaks = shiftRequest.getBreaks().stream()
                .map(ShiftBreakMapper::toDomain)
                .toList();

        validateBreaks(breaks, shiftRequest.getStartTime(), shiftRequest.getEndTime());

        Shift shift = new Shift(
                shiftRequest.getName(),
                shiftRequest.getStartTime(),
                shiftRequest.getEndTime(),
                shiftRequest.getRestTimeBetweenAppointments(),
                breaks,
                shiftRequest.getDayOfWeek(),
                resolveStatus(shiftRequest.getStatus()));

        var savedShift = shiftRepository.save(shift);

        return ShiftMapper.toResponse(savedShift);
    }

    private void validateBreaks(List<ShiftBreak> breaks, LocalTime shiftStartTime, LocalTime shiftEndTime) {
        if (breaks == null || breaks.isEmpty()) {
            return;
        }

        var sortedBreaks = breaks.stream()
                .peek(shiftBreak -> validateBreakRange(shiftBreak, shiftStartTime, shiftEndTime))
                .sorted(Comparator.comparing(ShiftBreak::getStartTime))
                .toList();

        for (int index = 1; index < sortedBreaks.size(); index++) {
            ShiftBreak previous = sortedBreaks.get(index - 1);
            ShiftBreak current = sortedBreaks.get(index);

            if (current.getStartTime().isBefore(previous.getEndTime())) {
                throw new ConflictException("Shift breaks cannot overlap");
            }
        }
    }

    private void validateBreakRange(ShiftBreak shiftBreak, LocalTime shiftStartTime, LocalTime shiftEndTime) {
        if (shiftBreak.getStartTime() == null || shiftBreak.getEndTime() == null) {
            throw new ConflictException("Shift break start time and end time are required");
        }

        if (!shiftBreak.getStartTime().isBefore(shiftBreak.getEndTime())) {
            throw new ConflictException(
                    "Shift break start time must be before end time");
        }

        if (shiftStartTime == null || shiftEndTime == null) {
            throw new ConflictException("Shift start time and end time are required");
        }

        if (shiftBreak.getStartTime().isBefore(shiftStartTime)
                || shiftBreak.getEndTime().isAfter(shiftEndTime)) {
            throw new ConflictException("Shift breaks must be inside shift start time and end time");
        }
    }

    @Transactional
    public ShiftResponse updateShift(Long id, ShiftUpdateRequest shiftRequest) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new ShiftNotFoundException(id));

        shift.setName(shiftRequest.getName());
        shift.setStartTime(shiftRequest.getStartTime());
        shift.setEndTime(shiftRequest.getEndTime());
        shift.setRestTimeBetweenAppointments(shiftRequest.getRestTimeBetweenAppointments());
        shift.setBreaks(shiftRequest.getBreaks().stream()
                .map(ShiftBreakMapper::toDomain)
                .toList());
        shift.setDayOfWeek(shiftRequest.getDayOfWeek());
        if (shiftRequest.getStatus() != null) {
            shift.setStatus(shiftRequest.getStatus());
        }

        validateBreaks(shift.getBreaks(), shift.getStartTime(), shift.getEndTime());

        var savedShift = shiftRepository.save(shift);
        return ShiftMapper.toResponse(savedShift);
    }

    @Transactional
    public void deleteShiftById(Long id) {
        shiftRepository.removeById(id);
    }

    private ShiftStatus resolveStatus(ShiftStatus status) {
        return status == null ? ShiftStatus.ENABLED : status;
    }
}
