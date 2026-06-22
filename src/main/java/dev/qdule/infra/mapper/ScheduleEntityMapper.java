package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Schedule;
import dev.qdule.infra.persistence.entities.ScheduleEntity;

public class ScheduleEntityMapper {
    public static Schedule toDomain(ScheduleEntity entity) {
        return new Schedule(
                entity.getId(),
                TreatmentEntityMapper.toDomain(entity.getTreatment()),
                ClientEntityMapper.toDomain(entity.getClient()),
                entity.getStartDateTime(),
                entity.getEndDateTime(),
                entity.getReason(),
                entity.getStatus());
    }

    public static ScheduleEntity toEntity(Schedule schedule) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setStartDateTime(schedule.getStartDateTime());
        entity.setEndDateTime(schedule.getEndDateTime());
        entity.setReason(schedule.getReason());
        entity.setStatus(schedule.getStatus());
        entity.setId(schedule.getId());
        entity.setTreatment(TreatmentEntityMapper.toEntity(schedule.getTreatment()));
        entity.setClient(ClientEntityMapper.toEntity(schedule.getClient()));
        return entity;
    }
}
