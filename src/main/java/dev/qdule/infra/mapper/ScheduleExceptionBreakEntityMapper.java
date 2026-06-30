package dev.qdule.infra.mapper;

import dev.qdule.domain.model.ScheduleExceptionBreak;
import dev.qdule.infra.persistence.entities.ScheduleExceptionBreakEntity;
import dev.qdule.infra.persistence.entities.ScheduleExceptionEntity;

public class ScheduleExceptionBreakEntityMapper {
    public static ScheduleExceptionBreak toDomain(ScheduleExceptionBreakEntity entity) {
        return new ScheduleExceptionBreak(
                entity.getId(),
                entity.getStartDateTime(),
                entity.getEndDateTime());
    }

    public static ScheduleExceptionBreakEntity toEntity(ScheduleExceptionBreak scheduleExceptionBreak,
            ScheduleExceptionEntity scheduleException) {
        ScheduleExceptionBreakEntity entity = new ScheduleExceptionBreakEntity();

        entity.setId(scheduleExceptionBreak.getId());
        entity.setScheduleException(scheduleException);
        entity.setStartDateTime(scheduleExceptionBreak.getStartDateTime());
        entity.setEndDateTime(scheduleExceptionBreak.getEndDateTime());

        return entity;
    }
}
