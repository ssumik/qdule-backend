package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.domain.model.Client;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.infra.mapper.ClientEntityMapper;
import dev.qdule.infra.persistence.panache.ClientRepositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClientRespositoryImpl implements ClientRepository {
    private ClientRepositoryPanache clientRepositoryPanache;

    @Inject
    public ClientRespositoryImpl(ClientRepositoryPanache clientRepositoryPanache) {
        this.clientRepositoryPanache = clientRepositoryPanache;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(clientRepositoryPanache.findById(id))
                .map(ClientEntityMapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return clientRepositoryPanache
                .find("email", email)
                .firstResultOptional()
                .map(ClientEntityMapper::toDomain);
    }

    @Override
    public Client save(Client client) {
        clientRepositoryPanache
                .persist(ClientEntityMapper.toEntity(client));
        return client;
    }

    @Override
    public void removeById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

}
