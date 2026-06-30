package dev.qdule.infra.mapper;

import dev.qdule.domain.model.ScheduleException;
import dev.qdule.infra.persistence.entities.ScheduleExceptionEntity;

public class ScheduleExceptionEntityMapper {
    public static ScheduleException toDomain(ScheduleExceptionEntity entity) {
        return new ScheduleException(
                entity.getId(),
                entity.getStartDateTime(),
                entity.getEndDateTime(),
                entity.getReason(),
                entity.getBreaks().stream()
                        .map(ScheduleExceptionBreakEntityMapper::toDomain)
                        .toList());
    }

    public static ScheduleExceptionEntity toEntity(ScheduleException scheduleException) {
        ScheduleExceptionEntity entity = new ScheduleExceptionEntity();

        entity.setId(scheduleException.getId());
        entity.setStartDateTime(scheduleException.getStartDateTime());
        entity.setEndDateTime(scheduleException.getEndDateTime());
        entity.setReason(scheduleException.getReason());
        entity.setBreaks(scheduleException.getBreaks().stream()
                .map(scheduleExceptionBreak -> ScheduleExceptionBreakEntityMapper.toEntity(
                        scheduleExceptionBreak,
                        entity))
                .toList());

        return entity;
    }
}
