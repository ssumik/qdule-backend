package dev.qdule.domain.repository;

import java.util.Optional;

import dev.qdule.domain.model.User;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void removeById(Long id);
}
