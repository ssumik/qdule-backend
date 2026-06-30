package dev.qdule.application.mapper;

import dev.qdule.application.dto.requests.ScheduleExceptionBreakRequest;
import dev.qdule.application.dto.responses.ScheduleExceptionBreakResponse;
import dev.qdule.domain.model.ScheduleExceptionBreak;

public class ScheduleExceptionBreakMapper {
    public static ScheduleExceptionBreakResponse toResponse(ScheduleExceptionBreak scheduleExceptionBreak) {
        ScheduleExceptionBreakResponse response = new ScheduleExceptionBreakResponse();

        response.setId(scheduleExceptionBreak.getId());
        response.setStartDateTime(scheduleExceptionBreak.getStartDateTime());
        response.setEndDateTime(scheduleExceptionBreak.getEndDateTime());

        return response;
    }

    public static ScheduleExceptionBreak toDomain(ScheduleExceptionBreakRequest request) {
        ScheduleExceptionBreak scheduleExceptionBreak = new ScheduleExceptionBreak();
        scheduleExceptionBreak.setStartDateTime(request.getStartDateTime());
        scheduleExceptionBreak.setEndDateTime(request.getEndDateTime());
        return scheduleExceptionBreak;
    }
}
