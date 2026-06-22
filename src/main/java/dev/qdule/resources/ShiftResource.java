package dev.qdule.resources;

import dev.qdule.application.dto.requests.ShiftCreateRequest;
import dev.qdule.application.dto.requests.ShiftUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.ShiftResponse;
import dev.qdule.application.services.ShiftService;
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

@Path("/shifts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShiftResource {
    private ShiftService shiftService;

    @Inject
    public ShiftResource(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShifts(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<ShiftResponse> response = shiftService.getShifts(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findShiftById(@PathParam("id") Long id) {
        ShiftResponse response = shiftService.getShiftById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createShift(ShiftCreateRequest request) {
        ShiftResponse response = shiftService.createShift(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateShift(@PathParam("id") Long id, ShiftUpdateRequest request) {
        ShiftResponse response = shiftService.updateShift(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteShiftById(@PathParam("id") Long id) {
        shiftService.deleteShiftById(id);
        return Response.noContent().build();
    }
}
