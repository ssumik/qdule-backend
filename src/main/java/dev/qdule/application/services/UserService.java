package dev.qdule.application.services;

import dev.qdule.application.dto.requests.UserCreateRequest;
import dev.qdule.application.dto.responses.UserResponse;
import dev.qdule.application.exception.UserNotFoundException;
import dev.qdule.application.mapper.UserMapper;
import dev.qdule.domain.model.User;
import dev.qdule.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {
    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse findUserById(Long id) {
        var user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        User user = new User(
                request.getName(),
                request.getPassword(),
                request.getEmail()
                // request.getRoles(),
                // request.getStatus(),
                );

        var response = userRepository.save(user);

        return UserMapper.toResponse(response);
    }

}
