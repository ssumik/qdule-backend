package dev.qdule.infra.mapper;

import dev.qdule.domain.model.User;
import dev.qdule.infra.persistence.entities.UserEntity;

public class UserEntityMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getPassword(),
                entity.getRoles(),
                entity.getStatus());
    }

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();

        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setPassword(user.getPassword());
        entity.setRoles(user.getRoles());
        entity.setStatus(user.getStatus());

        return entity;
    }
}
