package dev.qdule.resources.exception;

import java.time.LocalDateTime;

import dev.qdule.application.exception.ClientNotFoundException;
import dev.qdule.application.exception.ConflictException;
import dev.qdule.application.exception.EmailSendException;
import dev.qdule.application.exception.ScheduleExceptionNotFoundException;
import dev.qdule.application.exception.ScheduleNotFoundException;
import dev.qdule.application.exception.ShiftDisabledException;
import dev.qdule.application.exception.ShiftNotFoundException;
import dev.qdule.application.exception.TreatmentDisabledException;
import dev.qdule.application.exception.TreatmentNotFoundException;
import dev.qdule.application.exception.UserNotFoundException;
import io.quarkus.logging.Log;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// TODO: ADICIONAR EXCEPTIONS FALTANTES
@Provider
public class GlobalExceptionHandler
        implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof NotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    "Resource not found",
                    LocalDateTime.now());
            Log.warn("Resource not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof WebApplicationException webApplicationException) {
            int status = webApplicationException.getResponse().getStatus();
            ErrorResponse error = new ErrorResponse(
                    status,
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warnf("HTTP request failed with status %d: %s", status, exception.getMessage());
            return Response.status(status).entity(error).build();
        }

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
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ScheduleNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Schedule not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ScheduleExceptionNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Schedule exception not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof EmailSendException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.BAD_GATEWAY.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Email send failed: " + exception.getMessage());
            return Response.status(Response.Status.BAD_GATEWAY).entity(error).build();
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

        if (exception instanceof ShiftNotFoundException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.NOT_FOUND.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Shift not found: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(error).build();
        }

        if (exception instanceof ShiftDisabledException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.METHOD_NOT_ALLOWED.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Shift not found: " + exception.getMessage());
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(error).build();
        }

        if (exception instanceof TreatmentDisabledException) {
            ErrorResponse error = new ErrorResponse(
                    Response.Status.METHOD_NOT_ALLOWED.getStatusCode(),
                    exception.getMessage(),
                    LocalDateTime.now());
            Log.warn("Shift not found: " + exception.getMessage());
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(error).build();
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
