package dev.qdule.application.services;

import dev.qdule.application.dto.requests.AuthRequest;
import dev.qdule.application.dto.responses.AuthResponse;
import dev.qdule.application.exception.UserNotFoundException;
// import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.User;
import dev.qdule.domain.repository.ClientRepository;
import dev.qdule.domain.repository.UserRepository;
import dev.qdule.domain.repository.PasswordRepository;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {
    // @Inject
    // private ClientRepository clientRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    PasswordRepository passwordRepository;

    @Inject
    JwtService jwtService;

    // @Inject
    // public AuthService(ClientRepository clientRepository) {
    //     this.clientRepository = clientRepository;
    // }

    @Inject
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // public AuthResponse findUserByEmail(String email) {
    //     var client = clientRepository
    //             .findByEmail(email)
    //             .orElseThrow(() -> new UserNotFoundException(email)); 
    //     return AuthMapper.toResponse(client);
    // }

    public AuthResponse generateUserToken(AuthRequest request){


        // Client client = clientRepository.findByEmail(request.getEmail())
        //         .orElseThrow(() -> new UserNotFoundException(request.getEmail()));
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        if (!passwordRepository.matches(request.password, user.getPassword())) {
            throw new UnauthorizedException();
        }

        String token = jwtService.generate(user);

        return new AuthResponse(token);
    }
}
