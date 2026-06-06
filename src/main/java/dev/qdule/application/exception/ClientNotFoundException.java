package dev.qdule.application.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Client not found: " + id);
    }
}
