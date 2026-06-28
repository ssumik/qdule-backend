package dev.qdule.resources;

import dev.qdule.application.dto.requests.ChangelogCreateRequest;
import dev.qdule.application.dto.requests.ChangelogUpdateRequest;
import dev.qdule.application.dto.responses.ChangelogResponse;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.services.ChangelogService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

// TODO: ajustar o changelog 

@Path("/changelogs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChangelogResource {
    private ChangelogService changelogService;

    @Inject
    public ChangelogResource(ChangelogService changelogService) {
        this.changelogService = changelogService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Changelogs list",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PageResponse.class)
        )
    )
    public Response getChangelogs(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<ChangelogResponse> response = changelogService.getChangelogs(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Changelog details",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ChangelogResponse.class)
        )
    )
    public Response findChangelogById(@PathParam("id") Long id) {
        ChangelogResponse response = changelogService.getChangelogById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Changelog created",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ChangelogResponse.class)
        )
    )
    public Response createChangelog(ChangelogCreateRequest request) {
        ChangelogResponse response = changelogService.createChangelog(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Changelog updated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ChangelogResponse.class)
        )
    )
    public Response updateChangelog(@PathParam("id") Long id, ChangelogUpdateRequest request) {
        ChangelogResponse response = changelogService.updateChangelog(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponse(
        responseCode = "204",
        description = "Changelog deleted"
    )
    public Response deleteChangelogById(@PathParam("id") Long id) {
        changelogService.deleteChangelogById(id);
        return Response.noContent().build();
    }
}
