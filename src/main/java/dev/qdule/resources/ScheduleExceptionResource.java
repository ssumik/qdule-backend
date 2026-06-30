package dev.qdule.resources;

import dev.qdule.application.dto.requests.ScheduleExceptionCreateRequest;
import dev.qdule.application.dto.requests.ScheduleExceptionUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleExceptionResponse;
import dev.qdule.application.services.ScheduleExceptionService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/schedule-exceptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleExceptionResource {
    private ScheduleExceptionService scheduleExceptionService;

    @Inject
    public ScheduleExceptionResource(ScheduleExceptionService scheduleExceptionService) {
        this.scheduleExceptionService = scheduleExceptionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Schedule exceptions list", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PageResponse.class)))
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "size", in = ParameterIn.QUERY, required = true)
    })
    public Response getScheduleExceptions(
            @Parameter(required = true) @QueryParam("page") int page,
            @Parameter(required = true) @QueryParam("size") int size) {
        PageResponse<ScheduleExceptionResponse> response = scheduleExceptionService.getScheduleExceptions(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Schedule exception details", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ScheduleExceptionResponse.class)))
    public Response findScheduleExceptionById(@PathParam("id") Long id) {
        ScheduleExceptionResponse response = scheduleExceptionService.getScheduleExceptionById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Schedule exception created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ScheduleExceptionResponse.class)))
    public Response createScheduleException(ScheduleExceptionCreateRequest request) {
        ScheduleExceptionResponse response = scheduleExceptionService.createScheduleException(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Schedule exception updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ScheduleExceptionResponse.class)))
    public Response updateScheduleException(@PathParam("id") Long id, ScheduleExceptionUpdateRequest request) {
        ScheduleExceptionResponse response = scheduleExceptionService.updateScheduleException(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    @APIResponse(responseCode = "204", description = "Schedule exception deleted")
    public Response deleteScheduleExceptionById(@PathParam("id") Long id) {
        scheduleExceptionService.deleteScheduleExceptionById(id);
        return Response.noContent().build();
    }
}
