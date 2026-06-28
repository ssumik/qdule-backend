package dev.qdule.application.exception;

public class ShiftDisabledException extends RuntimeException {
    public ShiftDisabledException() {
        super("Schedule is disabled");
    }
}
