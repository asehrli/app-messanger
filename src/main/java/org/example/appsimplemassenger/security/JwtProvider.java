package org.example.appsimplemassenger.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtProvider {

    @Value("${token.secret-key}")
    String secretKey;

    @Value("${token.expires}")
    long expires;

    public String generateToken(String subject) {
        return JWT
                .create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expires))
                .sign(algorithm());
    }

    public String getSubjectFromToken(String token) {
        try {
            return JWT
                    .require(algorithm())
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secretKey);
    }

}
