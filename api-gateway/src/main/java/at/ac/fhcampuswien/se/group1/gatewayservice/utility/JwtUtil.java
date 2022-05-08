package at.ac.fhcampuswien.se.group1.gatewayservice.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${car-rental-app.jwt-secret}")
    private String secret;

    public DecodedJWT decodedJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Car Rental Application").build();

        return verifier.verify(token);
    }

    private boolean isTokenExpired(String token) {
        return this.decodedJWT(token).getExpiresAt().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}