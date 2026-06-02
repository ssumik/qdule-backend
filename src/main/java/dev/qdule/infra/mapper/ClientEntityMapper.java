package dev.qdule.infra.mapper;

import dev.qdule.domain.model.Client;
import dev.qdule.infra.persistence.entities.ClientEntity;

public class ClientEntityMapper {
    public static Client toDomain(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCellPhone(),
                null);
    }

    public static ClientEntity toEntity(Client client) {
        ClientEntity entity = new ClientEntity();

        entity.setId(client.getId());
        entity.setName(client.getName());
        entity.setEmail(client.getEmail());
        entity.setCellPhone(client.getCellPhone());

        return entity;
    }
}
