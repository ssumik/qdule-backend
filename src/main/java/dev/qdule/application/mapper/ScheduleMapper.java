package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.domain.model.Schedule;

public class ScheduleMapper {
    public static ScheduleResponse toResponse(Schedule schedule) {
        ScheduleResponse response = new ScheduleResponse();

        response.setId(schedule.getId());
        response.setTreatmentId(schedule.getTreatmentId());
        response.setClientId(schedule.getClientId());
        response.setStartDateTime(schedule.getStartDateTime());
        response.setEndDateTime(schedule.getEndDateTime());
        response.setReason(schedule.getReason());
        response.setStatus(schedule.getStatus());

        return response;
    }
}
