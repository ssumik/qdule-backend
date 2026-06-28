package dev.qdule.application.exception;

public class ShiftNotFoundException extends RuntimeException {
    public ShiftNotFoundException(Long id) {
        super("Shift not found with id: " + id);
    }

    public ShiftNotFoundException(String msg) {
        super(msg);
    }
}
