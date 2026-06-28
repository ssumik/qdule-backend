package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ShiftBreakResponse;
import dev.qdule.domain.model.ShiftBreak;

public class ShiftBreakMapper {
    public static ShiftBreakResponse toResponse(ShiftBreak shiftBreak) {
        ShiftBreakResponse response = new ShiftBreakResponse();

        response.setId(shiftBreak.getId());
        response.setStartTime(shiftBreak.getStartTime());
        response.setEndTime(shiftBreak.getEndTime());

        return response;
    }
}
