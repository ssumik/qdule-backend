package dev.qdule.application.exception;

public class ScheduleExceptionNotFoundException extends RuntimeException {
    public ScheduleExceptionNotFoundException(Long id) {
        super("ScheduleException not found with id: " + id);
    }
}
