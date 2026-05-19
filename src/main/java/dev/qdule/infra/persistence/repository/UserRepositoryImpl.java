package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.domain.model.User;
import dev.qdule.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> findById(Long id) {
        return null;
    }
}
