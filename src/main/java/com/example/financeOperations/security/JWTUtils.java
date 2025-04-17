package com.example.financeOperations.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.financeOperations.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtils {
    private static final Logger log = LogManager.getLogger(JWTUtils.class);

    @Value("${spring.privateKeyBase64}")
    private String privateKeyString;

    @Value("${spring.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJWT(User user) {
        String email = user.getEmail() + "$#$" + user.getUsername();
        byte [] secret = Base64.getDecoder().decode(privateKeyString);
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(alg);
    }
}
