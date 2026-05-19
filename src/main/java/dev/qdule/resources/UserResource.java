package dev.qdule.resources;

import dev.qdule.application.dto.responses.UserResponse;
import dev.qdule.application.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    private UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response findUserById(@PathParam("id") Long id) {
        UserResponse response = userService.findUserById(id);
        return Response.ok().entity(response).build();
    }

}