package at.ac.fhcampuswien.se.group1.authenticationservice.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${car-rental-app.jwt-secret}")
    private String secret;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("Car Rental Application")
                .withExpiresAt(DateUtils.addHours(new Date(), 4))
                .sign(Algorithm.HMAC256(secret));
    }
    
    public String validateTokenAndRetrieveSubject(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Car Rental Application").build();
    
        String subject = "";
        DecodedJWT jwt = verifier.verify(token);
        subject = jwt.getClaim("username").asString();
        
        return subject;
    }
}