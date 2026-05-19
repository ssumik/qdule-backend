package dev.qdule.application.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
