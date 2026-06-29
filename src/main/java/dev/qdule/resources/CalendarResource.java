package dev.qdule.resources;

import dev.qdule.application.dto.responses.CalendarListResponse;
import dev.qdule.application.services.CalendarService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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

@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalendarResource {
        private CalendarService calendarService;

        @Inject
        public CalendarResource(CalendarService calendarService) {
                this.calendarService = calendarService;
        }

        @GET
        @Path("/{year}/{month}/available")
        @Produces(MediaType.APPLICATION_JSON)
        @Parameters({
                        @Parameter(name = "year", in = ParameterIn.PATH, required = true),
                        @Parameter(name = "month", in = ParameterIn.PATH, required = true),
                        @Parameter(name = "treatmentId", in = ParameterIn.QUERY, required = true),
        })
        @APIResponse(responseCode = "200", description = "Avaliable times to schedule", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CalendarListResponse.class)))
        public Response availableSchedule(
                        @PathParam("year") int year,
                        @PathParam("month") int month,
                        @QueryParam("treatmentId") Long treatmentId) {
                CalendarListResponse response = calendarService.availableSchedule(
                                treatmentId,
                                year,
                                month);
                return Response.ok().entity(response).build();
        }

        @GET
        @Path("/{year}/{month}/scheduled")
        @Produces(MediaType.APPLICATION_JSON)
        @Parameters({
                        @Parameter(name = "year", in = ParameterIn.PATH, required = true),
                        @Parameter(name = "month", in = ParameterIn.PATH, required = true),
        })
        @APIResponse(responseCode = "200", description = "Avaliable times to schedule", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CalendarListResponse.class)))
        public Response scheduledTreatment(
                        @PathParam("year") int year,
                        @PathParam("month") int month) {
                CalendarListResponse response = calendarService.scheduledTreatment(
                                year,
                                month);
                return Response.ok().entity(response).build();
        }
}
