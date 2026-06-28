package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Shift;
import dev.qdule.infra.persistence.entities.ShiftEntity;

public class ShiftEntityMapper {
    public static Shift toDomain(ShiftEntity entity) {
        return new Shift(
                entity.getId(),
                entity.getName(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getRestTimeBetweenAppointments(),
                entity.getBreaks().stream()
                        .map(ShiftBreakEntityMapper::toDomain)
                        .toList());
    }

    public static ShiftEntity toEntity(Shift shift) {
        ShiftEntity entity = new ShiftEntity();

        entity.setId(shift.getId());
        entity.setName(shift.getName());
        entity.setStartTime(shift.getStartTime());
        entity.setEndTime(shift.getEndTime());
        entity.setRestTimeBetweenAppointments(shift.getRestTimeBetweenAppointments());
        entity.setBreaks(shift.getBreaks().stream()
                .map(shiftBreak -> ShiftBreakEntityMapper.toEntity(shiftBreak, entity))
                .toList());

        return entity;
    }
}
