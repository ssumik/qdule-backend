package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ShiftResponse;
import dev.qdule.domain.model.Shift;

public class ShiftMapper {
    public static ShiftResponse toResponse(Shift shift) {
        ShiftResponse response = new ShiftResponse();

        response.setId(shift.getId());
        response.setName(shift.getName());
        response.setStartTime(shift.getStartTime());
        response.setEndTime(shift.getEndTime());
        response.setRestTimeBetweenAppointments(shift.getRestTimeBetweenAppointments());
        response.setBreaks(shift.getBreaks().stream()
                .map(ShiftBreakMapper::toResponse)
                .toList());

        return response;
    }
}
