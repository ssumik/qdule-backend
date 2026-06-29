package dev.qdule.application.mapper;

import java.util.List;

import dev.qdule.application.dto.responses.ShiftListResponse;
import dev.qdule.domain.model.Shift;

public class ShiftListMapper {
    public static ShiftListResponse toResponse(List<Shift> shifts) {
        ShiftListResponse response = new ShiftListResponse();

        var shiftResponse = shifts.stream().map(ShiftMapper::toResponse).toList();

        response.setWorkShifts(shiftResponse);

        return response;
    }
}
