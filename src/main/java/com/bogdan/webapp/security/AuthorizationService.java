package com.bogdan.webapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.bogdan.webapp.properties.AuthorizationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Service
public class AuthorizationService {

    @Autowired
    private static AuthorizationProperties authorizationProperties;

    public static String generateJwtToken(int userId, String username) {

        String jwtToken = null;
        try {
            //final muta in application properties si le incarci ca si la admin props
             final Integer hours = authorizationProperties.getHours();
             final String algorithmSecret = authorizationProperties.getAlgorithmSecret();
             final String issuer = authorizationProperties.getIssuer();

            Algorithm algorithm = Algorithm.HMAC256(algorithmSecret);
            //inlocuieste Calendar cu Java 8 stuff e.g LocalDate etc
            jwtToken = JWT.create().withIssuer(issuer).withSubject(String.valueOf(userId)).withIssuedAt(Date.from(Instant.from(LocalDateTime.now())))
                    .withExpiresAt(Date.from(Instant.from(LocalDateTime.now().plusHours(hours)))).withClaim("username", username).sign(algorithm);
        } catch (JWTCreationException e) {
            jwtToken = null;
        }

        return jwtToken;
    }
}
