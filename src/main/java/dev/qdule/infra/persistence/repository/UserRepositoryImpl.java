package dev.qdule.infra.persistence.repository;

import java.util.Optional;

import dev.qdule.application.exception.UserNotFoundException;
import dev.qdule.domain.model.User;
import dev.qdule.domain.repository.UserRepository;
import dev.qdule.infra.mapper.UserEntityMapper;
import dev.qdule.infra.persistence.entities.UserEntity;
import dev.qdule.infra.persistence.panache.UserRespositoryPanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    private UserRespositoryPanache userRespositoryPanache;

    @Inject
    public UserRepositoryImpl(UserRespositoryPanache userRespositoryPanache) {
        this.userRespositoryPanache = userRespositoryPanache;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(
                userRespositoryPanache.findById(id))
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public User save(User user) {
        userRespositoryPanache.persist(UserEntityMapper.toEntity(user));
        return user;
    }

    @Override
    public void removeById(Long id) {
        if (!userRespositoryPanache.deleteById(id))
            throw new UserNotFoundException(id);
    }
}
