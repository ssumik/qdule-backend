package dev.qdule.application.exception;

public class ChangelogNotFoundException extends RuntimeException {
    public ChangelogNotFoundException(Long id) {
        super("Changelog not found with id: " + id);
    }
}
