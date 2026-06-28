package dev.qdule.infra.mapper;

import dev.qdule.domain.model.ShiftBreak;
import dev.qdule.infra.persistence.entities.ShiftBreakEntity;
import dev.qdule.infra.persistence.entities.ShiftEntity;

public class ShiftBreakEntityMapper {
    public static ShiftBreak toDomain(ShiftBreakEntity entity) {
        return new ShiftBreak(
                entity.getId(),
                entity.getStartTime(),
                entity.getEndTime());
    }

    public static ShiftBreakEntity toEntity(ShiftBreak shiftBreak, ShiftEntity shift) {
        ShiftBreakEntity entity = new ShiftBreakEntity();

        entity.setId(shiftBreak.getId());
        entity.setShift(shift);
        entity.setStartTime(shiftBreak.getStartTime());
        entity.setEndTime(shiftBreak.getEndTime());

        return entity;
    }
}
