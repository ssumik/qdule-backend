package dev.qdule.resources;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.services.ScheduleService;
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

@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {
    private ScheduleService scheduleService;

    @Inject
    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Schedules list",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PageResponse.class)
        )
    )
    public Response getSchedules(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<ScheduleResponse> response = scheduleService.getSchedules(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Schedule details",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ScheduleResponse.class)
        )
    )
    public Response findScheduleById(@PathParam("id") Long id) {
        ScheduleResponse response = scheduleService.getScheduleById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Schedule created",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ScheduleResponse.class)
        )
    )
    public Response createSchedule(ScheduleCreateRequest request) {
        ScheduleResponse response = scheduleService.createSchedule(request);
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
        description = "Schedule updated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ScheduleResponse.class)
        )
    )
    public Response updateSchedule(@PathParam("id") Long id, ScheduleUpdateRequest request) {
        ScheduleResponse response = scheduleService.updateSchedule(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    @APIResponse(
        responseCode = "204",
        description = "Schedule deleted"
    )
    public Response deleteScheduleById(@PathParam("id") Long id) {
        scheduleService.deleteScheduleById(id);
        return Response.noContent().build();
    }
}
