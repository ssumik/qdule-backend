package dev.qdule.resources.exception;

import java.time.LocalDateTime;

import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.exception.UserNotFoundException;
import io.quarkus.logging.Log;
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
            Log.warn("User not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ClientNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Client not found: " + exception.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        if (exception instanceof ConflictException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.CONFLICT.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Conflict found: " + exception.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        if (exception instanceof TreatmentNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Treatment not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal server error: " + exception.getMessage(),
                LocalDateTime.now());

        Log.errorf(exception, "Internal server error");

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