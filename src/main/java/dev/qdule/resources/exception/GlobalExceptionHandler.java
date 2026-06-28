package dev.qdule.resources.exception;

import java.time.ZonedDateTime;

import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.ShiftDisabledException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.exception.UserNotFoundException;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// TODO: ADICIONAR EXCEPTIONS FALTANTES
@Provider
public class GlobalExceptionHandler
        implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof UserNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("User not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ClientNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("Client not found: " + exception.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        if (exception instanceof ConflictException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.CONFLICT.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("Conflict found: " + exception.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(error).build();
        }

        if (exception instanceof TreatmentNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("Treatment not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ShiftNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("Shift not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ShiftDisabledException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.METHOD_NOT_ALLOWED.getStatusCode(),
                    exception.getMessage(),
                    ZonedDateTime.now());
            Log.warn("Shift not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        ErrorResponse error = new ErrorResponse(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "Internal server error: " + exception.getMessage(),
                ZonedDateTime.now());

        Log.errorf(exception, "Internal server error");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .build();
    }

    public static class ErrorResponse {
        public int status;
        public String message;
        public ZonedDateTime timestamp;

        public ErrorResponse(int status, String message, ZonedDateTime timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}