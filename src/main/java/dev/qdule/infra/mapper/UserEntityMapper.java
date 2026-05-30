package dev.qdule.infra.mapper;

import dev.qdule.domain.model.User;
import dev.qdule.infra.persistence.entities.UserEntity;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName());
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setName(user.getName());

        return entity;
    }
}
