package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.domain.model.Client;

public interface ClientRepository {
    Optional<Client> findById(Long id);

    Optional<Client> findByEmail(String email);

    Client save(Client user);

    void removeById(Long id);
}
