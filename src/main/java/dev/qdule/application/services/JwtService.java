package dev.qdule.application.services;

import java.time.Duration;

// import dev.qdule.domain.model.Client;
import dev.qdule.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

    public String generate(User user) {

        return io.smallrye.jwt.build.Jwt.issuer("qdule")
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("type", "USER")
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}