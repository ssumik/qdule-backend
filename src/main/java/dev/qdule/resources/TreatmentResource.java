package dev.qdule.resources;

import dev.qdule.application.dto.requests.TreatmentCreateRequest;
import dev.qdule.application.dto.requests.TreatmentUpdateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.TreatmentResponse;
import dev.qdule.application.services.TreatmentService;
import dev.qdule.domain.model.TreatmentType;
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

@Path("/treatments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TreatmentResource {
    private TreatmentService treatmentService;

    @Inject
    public TreatmentResource(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Treatments list",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = PageResponse.class)
        )
    )
    public Response getTreatments(
        @QueryParam("page") int page,
        @QueryParam("size") int size,
        @QueryParam("type") TreatmentType type
    ) {
        PageResponse<TreatmentResponse> response = treatmentService.getTreatments(page, size, type);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Treatment details",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TreatmentResponse.class)
        )
    )
    public Response findTreatmentById(@PathParam("id") Long id) {
        TreatmentResponse response = treatmentService.getTreatmentById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Treatment created",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TreatmentResponse.class)
        )
    )
    public Response createTreatment(TreatmentCreateRequest request) {
        TreatmentResponse response = treatmentService.createTreatment(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @DELETE
    @Authenticated
    @Path("/{id}")
    @APIResponse(
        responseCode = "204",
        description = "Treatment deleted"
    )
    public Response deleteTreatmentById(@PathParam("id") Long id) {
        treatmentService.deleteTreatmentById(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Treatment updated",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = TreatmentResponse.class)
        )
    )
    public Response updateTreatment(@PathParam("id") Long id, TreatmentUpdateRequest request) {
        TreatmentResponse response = treatmentService.updateTreatment(id, request);
        return Response.ok().entity(response).build();
    }
}