package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.UserResponse;
import dev.qdule.domain.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

}
