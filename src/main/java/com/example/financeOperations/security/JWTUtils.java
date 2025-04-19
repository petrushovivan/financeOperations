package com.example.financeOperations.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.financeOperations.models.User;
import jakarta.servlet.http.HttpServletRequest;
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
    private String secretKey;

    @Value("${spring.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJWT(User user) {
        String email = user.getEmail() + "$#$" + user.getUsername();
        byte [] secret = Base64.getDecoder().decode(secretKey);
        Algorithm alg = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(alg);
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.contains("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getEmailFromJWT(String token) {
        try {
            byte[] secret = Base64.getDecoder().decode(secretKey);
            Algorithm alg = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(alg).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String[] emailAndName = decodedJWT.getSubject().split("\\$#\\$");

            if(emailAndName.length != 2) {
                log.error("Incorrect JWT");
                return null;
            }

            return emailAndName[0];
        } catch (Exception e) {
            log.error("some error in username getting {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJWT(String tokenToCheck) {
        try {
            byte[] secret = Base64.getDecoder().decode(secretKey);
            Algorithm alg = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(alg).build();
            DecodedJWT decodedJWT = verifier.verify(tokenToCheck);

            log.debug("all ok");
            return true;
        } catch (Exception e) {
            log.debug("some error {}", e.getMessage());
            return false;
        }
    }
}
