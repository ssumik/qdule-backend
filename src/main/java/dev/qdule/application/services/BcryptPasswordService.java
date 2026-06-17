package dev.qdule.application.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

import dev.qdule.domain.repository.PasswordRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BcryptPasswordService implements PasswordRepository {

    @Override
    public String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}