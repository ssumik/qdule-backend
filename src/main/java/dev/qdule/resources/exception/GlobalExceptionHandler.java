package dev.qdule.resources.exception;

import java.time.LocalDateTime;

import dev.qdule.application.exception.UserNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler
        implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof UserNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());

            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal server error",
                LocalDateTime.now());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }

    public static class ErrorResponse {
        public int status;
        public String message;
        public LocalDateTime timestamp;

        public ErrorResponse(int status, String message, LocalDateTime timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}