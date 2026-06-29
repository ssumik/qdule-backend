package dev.qdule.resources;

import dev.qdule.application.dto.requests.ShiftCreateRequest;
import dev.qdule.application.dto.requests.ShiftUpdateRequest;
import dev.qdule.application.dto.responses.ShiftResponse;
import dev.qdule.application.services.ShiftService;
import dev.qdule.domain.model.ShiftStatus;
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

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

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
    @APIResponse(responseCode = "200", description = "Shifts list", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = ShiftResponse.class)))
    public Response getShifts(
            @QueryParam("status") ShiftStatus status, @QueryParam("days") List<DayOfWeek> days) {
        var response = shiftService.getShifts(status, days);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Authenticated
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Shift details", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShiftResponse.class)))
    public Response findShiftById(@PathParam("id") Long id) {
        ShiftResponse response = shiftService.getShiftById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Shift created", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShiftResponse.class)))
    public Response createShift(ShiftCreateRequest request) {
        ShiftResponse response = shiftService.createShift(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Shift updated", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ShiftResponse.class)))
    public Response updateShift(@PathParam("id") Long id, ShiftUpdateRequest request) {
        ShiftResponse response = shiftService.updateShift(id, request);
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Authenticated
    @Path("/{id}")
    @APIResponse(responseCode = "204", description = "Shift deleted")
    public Response deleteShiftById(@PathParam("id") Long id) {
        shiftService.deleteShiftById(id);
        return Response.noContent().build();
    }
}
