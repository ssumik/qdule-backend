package dev.qdule.application.mapper;

import dev.qdule.application.dto.responses.ClientResponse;
import dev.qdule.domain.model.Client;

public class ClientMapper {
    public static ClientResponse toResponse(Client client) {
        return new ClientResponse(client.getId(), client.getName(), client.getEmail(), client.getCellPhone(),
                client.getUser());
    }
}
