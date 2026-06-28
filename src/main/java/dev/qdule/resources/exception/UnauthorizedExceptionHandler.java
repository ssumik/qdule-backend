package dev.qdule.resources.exception;

import dev.qdule.resources.exception.GlobalExceptionHandler.ErrorResponse;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException exception) {

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse(
                        401,
                        "Token ausente ou inválido",
                        java.time.LocalDateTime.now()))
                .build();
    }
}
