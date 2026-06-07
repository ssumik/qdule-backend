package dev.qdule.application.exception;

public class TreatmentNotFoundException extends RuntimeException {
    public TreatmentNotFoundException(Long id) {
        super("Treatment not found with id: " + id);
    }

}
