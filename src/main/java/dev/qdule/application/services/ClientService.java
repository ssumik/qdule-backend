package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ClientCreateRequest;
import dev.qdule.application.dto.responses.ClientResponse;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.mapper.ClientMapper;
import dev.qdule.domain.model.Client;
import dev.qdule.domain.repository.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClientService {
    private ClientRepository clientRepository;

    @Inject
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ClientResponse createClient(ClientCreateRequest request) {
        clientRepository.findByEmail(request.getEmail()).ifPresent(client -> {
            throw new ConflictException("Email already exists");
        });

        Client client = new Client(
                request.getName(),
                request.getEmail(),
                request.getCellPhone());

        var response = clientRepository.save(client);

        return ClientMapper.toResponse(response);
    }

}
