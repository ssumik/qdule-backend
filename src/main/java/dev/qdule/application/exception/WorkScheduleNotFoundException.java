package dev.qdule.application.exception;

public class WorkScheduleNotFoundException extends RuntimeException {
    public WorkScheduleNotFoundException(Long id) {
        super("WorkSchedule not found with id: " + id);
    }
}
