package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.WorkScheduleResponse;
import dev.qdule.domain.model.WorkSchedule;

public class WorkScheduleMapper {
    public static WorkScheduleResponse toResponse(WorkSchedule workSchedule) {
        WorkScheduleResponse response = new WorkScheduleResponse();

        response.setId(workSchedule.getId());
        response.setShift(workSchedule.getShift());
        response.setDayOfWeek(workSchedule.getDayOfWeek());

        return response;
    }
}
