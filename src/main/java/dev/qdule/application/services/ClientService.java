package dev.qdule.application.services;

import dev.qdule.application.dto.requests.ClientCreateRequest;
import dev.qdule.application.dto.requests.ClientUpdateRequest;
import dev.qdule.application.dto.responses.ClientResponse;
import dev.qdule.application.exception.ClientNotFoundException;
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

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.removeById(id);
    }

    public ClientResponse findClientById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return ClientMapper.toResponse(client);
    }

    @Transactional
    public ClientResponse updateClientById(Long id, ClientUpdateRequest request) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository
                .findByEmail(request.getEmail())
                .ifPresent((entity) -> {
                    if (entity.getId() != id) {
                        throw new ConflictException("A client already has this email");
                    }
                });
        ;

        client.setEmail(request.getEmail());
        client.setCellPhone(request.getCellPhone());
        client.setName(request.getName());

        var response = clientRepository.save(client);

        return ClientMapper.toResponse(response);
    }
}
