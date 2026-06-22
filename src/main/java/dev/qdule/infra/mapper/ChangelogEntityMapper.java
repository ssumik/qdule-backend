package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Changelog;
import dev.qdule.infra.persistence.entities.ChangelogEntity;

public class ChangelogEntityMapper {
    public static Changelog toDomain(ChangelogEntity entity) {
        return new Changelog(
                entity.getId(),
                entity.getDateTime(),
                entity.getDescription(),
                entity.getSchedule().getId());
    }

    public static ChangelogEntity toEntity(Changelog changelog, ChangelogEntity entity) {
        entity.setDateTime(changelog.getDateTime());
        entity.setDescription(changelog.getDescription());
        return entity;
    }
}
