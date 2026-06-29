package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Config;
import dev.qdule.infra.persistence.entities.ConfigEntity;

public class ConfigEntityMapper {
    public static Config toDomain(ConfigEntity entity) {
        return new Config(
                entity.getId(),
                entity.getSendEmail(),
                entity.getContactLink());
    }

    public static ConfigEntity toEntity(Config config) {
        ConfigEntity entity = new ConfigEntity();

        entity.setId(config.getId());
        entity.setSendEmail(config.getSendEmail());
        entity.setContactLink(config.getContactLink());

        return entity;
    }
}
