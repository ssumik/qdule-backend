package dev.qdule.resources;

import dev.qdule.application.dto.requests.AuthRequest;
import dev.qdule.application.dto.responses.AuthResponse;
import dev.qdule.application.services.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    private AuthService authService;

    @Inject AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "User token generated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = AuthResponse.class)
        )
    )
    public Response generateUserToken(AuthRequest request) {
        AuthResponse response = authService.generateUserToken(request);
        return Response.status(Response.Status.OK).entity(response).build();
    }
}
