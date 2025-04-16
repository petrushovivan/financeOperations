package com.example.financeOperations.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.financeOperations.models.User;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;

import javax.crypto.KeyGenerator;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class JWTUtils {
    @Value("${spring.privateKeyString}")
    private String privateKeyString;
    @Value("${spring.jwtExpirationMs}")
    private String jwtExpirationMs;

    private final String publicKeyString;

    public JWTUtils() {
        publicKeyString = "very secret key";
    }

    public String generateJWT(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String username = user.getUsername();

        Algorithm alg = Algorithm.RSA256(publicKey(publicKeyString), privateKey(privateKeyString));

        return JWT.create().withSubject(username)
                .sign(alg);
    }

    private RSAPublicKey publicKey(String publicKeyString) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte [] bytes = publicKeyString.getBytes();
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec key = new X509EncodedKeySpec(bytes);
        return (RSAPublicKey) factory.generatePublic(key);
    }

    private RSAPrivateKey privateKey(String privateKeyString) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte [] bytes = privateKeyString.getBytes();
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec key = new X509EncodedKeySpec(bytes);
        return (RSAPrivateKey) factory.generatePrivate(key);
    }
}
