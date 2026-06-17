package dev.qdule.domain.repository;

public interface PasswordRepository {
    
    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
