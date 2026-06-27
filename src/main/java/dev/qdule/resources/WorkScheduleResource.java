package dev.qdule.resources;

import dev.qdule.application.dto.requests.WorkScheduleCreateRequest;
import dev.qdule.application.dto.requests.WorkScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.WorkScheduleResponse;
import dev.qdule.application.services.WorkScheduleService;
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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/work-schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkScheduleResource {
    private WorkScheduleService workScheduleService;

    @Inject
    public WorkScheduleResource(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Work schedules list",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PageResponse.class)
        )
    )
    public Response getWorkSchedules(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<WorkScheduleResponse> response = workScheduleService.getWorkSchedules(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Work schedule details",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WorkScheduleResponse.class)
        )
    )
    public Response findWorkScheduleById(@PathParam("id") Long id) {
        WorkScheduleResponse response = workScheduleService.getWorkScheduleById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Work schedule created",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WorkScheduleResponse.class)
        )
    )
    public Response createWorkSchedule(WorkScheduleCreateRequest request) {
        WorkScheduleResponse response = workScheduleService.createWorkSchedule(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Work schedule updated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WorkScheduleResponse.class)
        )
    )
    public Response updateWorkSchedule(@PathParam("id") Long id, WorkScheduleUpdateRequest request) {
        WorkScheduleResponse response = workScheduleService.updateWorkSchedule(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Authenticated
    @Path("/{id}")
    @APIResponse(
        responseCode = "204",
        description = "Work schedule deleted"
    )
    public Response deleteWorkScheduleById(@PathParam("id") Long id) {
        workScheduleService.deleteWorkScheduleById(id);
        return Response.noContent().build();
    }
}
