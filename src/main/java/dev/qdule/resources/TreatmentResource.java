package dev.qdule.resources;

import dev.qdule.application.dto.requests.TreatmentCreateRequest;
import dev.qdule.application.dto.requests.UserCreateRequest;
import dev.qdule.application.dto.responses.PageResponse;
import dev.qdule.application.dto.responses.TreatmentResponse;
import dev.qdule.application.dto.responses.UserResponse;
import dev.qdule.application.services.TreatmentService;
import dev.qdule.application.services.UserService;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

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
    public Response getTreatments(@QueryParam("page") int page, @QueryParam("size") int size) {
        PageResponse<TreatmentResponse> response = treatmentService.getTreatments(page, size);
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTreatmentById(@PathParam("id") Long id) {
        TreatmentResponse response = treatmentService.getTreatmentById(id);
        return Response.ok().entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTreatment(TreatmentCreateRequest request) {
        TreatmentResponse response = treatmentService.createTreatment(request);
        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTreatmentById(@PathParam("id") Long id) {
        treatmentService.deleteTreatmentById(id);
        return Response.noContent().build();
    }
}