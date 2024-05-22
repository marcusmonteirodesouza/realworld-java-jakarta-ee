package com.marcusmonteirodesouza.rest.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marcusmonteirodesouza.rest.user.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Date;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthService {
    @Inject
    @ConfigProperty(name = "JWT_EXPIRES_IN_SECONDS")
    private Integer jwtExpiresInSeconds;

    @Inject
    @ConfigProperty(name = "JWT_ISSUER")
    private String jwtIssuer;

    @Inject
    @ConfigProperty(name = "JWT_SECRET_KEY")
    private String jwtSecretKey;

    public String createJWT(User user) {
        return JWT.create()
                .withSubject(user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiresInSeconds * 1000))
                .withIssuer(this.jwtIssuer)
                .sign(this.getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(this.jwtIssuer);
    }
}
