package dev.qdule.resources;

import dev.qdule.application.dto.requests.ConfigUpdateRequest;
import dev.qdule.application.dto.responses.ConfigResponse;
import dev.qdule.application.services.ConfigService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/configs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigResource {
    private ConfigService configService;

    @Inject
    public ConfigResource(ConfigService configService) {
        this.configService = configService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Config details", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ConfigResponse.class)))
    public Response getConfig() {
        ConfigResponse response = configService.getConfig();
        return Response.ok().entity(response).build();
    }

    @PUT
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Config updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ConfigResponse.class)))
    public Response updateConfig(ConfigUpdateRequest request) {
        ConfigResponse response = configService.updateConfig(request);
        return Response.ok().entity(response).build();
    }
}
