package dev.qdule.application.exception;

public class TreatmentDisabledException extends RuntimeException {
    public TreatmentDisabledException(Long id) {
        super("Treatment is disabled: " + id);
    }
}
