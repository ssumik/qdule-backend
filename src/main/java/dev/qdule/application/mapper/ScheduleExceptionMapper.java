package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ScheduleExceptionResponse;
import dev.qdule.domain.model.ScheduleException;

public class ScheduleExceptionMapper {
    public static ScheduleExceptionResponse toResponse(ScheduleException scheduleException) {
        ScheduleExceptionResponse response = new ScheduleExceptionResponse();

        response.setId(scheduleException.getId());
        response.setStartDateTime(scheduleException.getStartDateTime());
        response.setEndDateTime(scheduleException.getEndDateTime());
        response.setReason(scheduleException.getReason());
        response.setBreaks(scheduleException.getBreaks().stream()
                .map(ScheduleExceptionBreakMapper::toResponse)
                .toList());

        return response;
    }
}
