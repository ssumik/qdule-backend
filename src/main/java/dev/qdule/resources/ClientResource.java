package dev.qdule.resources;

import dev.qdule.application.dto.requests.ClientCreateRequest;
import dev.qdule.application.dto.requests.ClientUpdateRequest;
import dev.qdule.application.dto.responses.ClientResponse;
import dev.qdule.application.services.ClientService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

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
    // @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Client created",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ClientResponse.class)
        )
    )
    public Response createClient(ClientCreateRequest request) {
        ClientResponse response = clientService.createClient(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "204",
        description = "Client deleted"
    )
    public Response deleteClient(@PathParam("id") Long id) {
        clientService.deleteClient(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Client details",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ClientResponse.class)
        )
    )
    public Response findClientById(@PathParam("id") Long id) {
        ClientResponse response = clientService.findClientById(id);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Client updated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ClientResponse.class)
        )
    )
    public Response updateClient(@PathParam("id") Long id, ClientUpdateRequest request) {
        ClientResponse response = clientService.updateClientById(id, request);
        return Response.status(Response.Status.OK).entity(response).build();
    }

}