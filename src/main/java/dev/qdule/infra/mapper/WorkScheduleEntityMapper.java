package dev.qdule.infra.mapper;

import dev.qdule.domain.model.WorkSchedule;
import dev.qdule.infra.persistence.entities.WorkScheduleEntity;

public class WorkScheduleEntityMapper {
    public static WorkSchedule toDomain(WorkScheduleEntity entity) {
        return new WorkSchedule(
                entity.getId(),
                ShiftEntityMapper.toDomain(entity.getShift()),
                entity.getDayOfWeek());
    }

    public static WorkScheduleEntity toEntity(WorkSchedule workSchedule) {
        WorkScheduleEntity entity = new WorkScheduleEntity();
        entity.setDayOfWeek(workSchedule.getDayOfWeek());
        entity.setId(workSchedule.getId());
        entity.setShift(ShiftEntityMapper.toEntity(workSchedule.getShift()));
        return entity;
    }
}
