package dev.qdule.resources;

import dev.qdule.application.dto.requests.ClientCreateRequest;
import dev.qdule.application.dto.responses.ClientResponse;
import dev.qdule.application.services.ClientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {
    private ClientService clientService;

    @Inject
    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClient(ClientCreateRequest request) {
        ClientResponse response = clientService.createClient(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}