package dev.qdule.application.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String id, String msg) {
        super("Conflict found for id: " + id + " - " + msg);
    }

    public ConflictException(String msg) {
        super("Conflict found: " + msg);
    }
}
