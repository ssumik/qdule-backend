package dev.qdule.infra.mapper;

import dev.qdule.domain.model.User;
import dev.qdule.infra.persistence.entities.UserEntity;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName());
    }

    public static UserEntity toEntity(User service) {
        UserEntity entity = new UserEntity();

        entity.setId(service.getId());
        entity.setName(service.getName());

        return entity;
    }
}
