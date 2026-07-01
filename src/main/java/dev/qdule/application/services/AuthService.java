package dev.qdule.application.services;

import dev.qdule.application.dto.requests.AuthRequest;
import dev.qdule.application.dto.responses.AuthResponse;
import dev.qdule.application.exception.UserNotFoundException;
import dev.qdule.domain.model.User;
import dev.qdule.domain.repository.UserRepository;
import dev.qdule.domain.repository.PasswordRepository;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    PasswordRepository passwordRepository;

    @Inject
    JwtService jwtService;

    @Inject
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse generateUserToken(AuthRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        if (!passwordRepository.matches(request.password, user.getPassword())) {
            throw new UnauthorizedException();
        }

        String token = jwtService.generate(user);

        return new AuthResponse(token);
    }
}
