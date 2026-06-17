package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.AuthResponse;
import dev.qdule.domain.model.Client;

public class AuthMapper {

    public static AuthResponse toResponse(Client client) {
        return new AuthResponse(client.getEmail());
    }
}
