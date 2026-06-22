package dev.qdule.resources;

import dev.qdule.application.dto.requests.ScheduleCreateRequest;
import dev.qdule.application.dto.requests.ScheduleUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ScheduleResponse;
import dev.qdule.application.services.ScheduleService;
import jakarta.ws.rs.core.MediaType;
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
import jakarta.ws.rs.core.Response;

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
    public Response getSchedules(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<ScheduleResponse> response = scheduleService.getSchedules(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findScheduleById(@PathParam("id") Long id) {
        ScheduleResponse response = scheduleService.getScheduleById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSchedule(ScheduleCreateRequest request) {
        ScheduleResponse response = scheduleService.createSchedule(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSchedule(@PathParam("id") Long id, ScheduleUpdateRequest request) {
        ScheduleResponse response = scheduleService.updateSchedule(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteScheduleById(@PathParam("id") Long id) {
        scheduleService.deleteScheduleById(id);
        return Response.noContent().build();
    }
}
