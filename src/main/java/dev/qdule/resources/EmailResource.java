package dev.qdule.resources;

import dev.qdule.application.dto.requests.EmailSendRequest;
import dev.qdule.application.dto.responses.EmailSendResponse;
import dev.qdule.application.services.EmailService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/send")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {
    private EmailService emailService;

    @Inject
    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }

    @POST
    @APIResponse(responseCode = "200", description = "Email sent", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EmailSendResponse.class)))
    public Response send(EmailSendRequest request) {
        EmailSendResponse response = emailService.send(request);
        return Response.ok().entity(response).build();
    }
}
