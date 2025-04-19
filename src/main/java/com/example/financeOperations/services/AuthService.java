package com.example.financeOperations.services;

import com.example.financeOperations.models.User;
import com.example.financeOperations.models.auth.AuthRequest;
import com.example.financeOperations.repositories.UserRepository;
import com.example.financeOperations.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> register(AuthRequest authRequest) {
        String email = authRequest.getEmail();
        User user = userRepository.findUserByEmail(email);
        if(user != null) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "this email is busy");
            body.put("status", false);
            return ResponseEntity.badRequest().body(body);
        }
        else {
            userRepository.save(new User(authRequest.getUsername(), authRequest.getEmail(), authRequest.getPassword()));
            Map<String, Object> body = new HashMap<>();
            body.put("message", "register successful");
            body.put("status", true);
            return ResponseEntity.ok(body);
        }
    }

    public ResponseEntity<?> login(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        User userToLogin = new User(username, email, password);

        User user = userRepository.findUserByEmail(email);

        if(user != null && user.equals(userToLogin)) {
            Map<String, Object> body = new HashMap<>();
            String userJWTToken = jwtUtils.generateJWT(user);
            body.put("message", "login successful");
            body.put("status", true);
            body.put("token", userJWTToken);

            user.setToken(userJWTToken);

            userRepository.save(user);

            return ResponseEntity.ok().body(body);
        }
        else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "incorrect credentials");
            body.put("status", false);
            return ResponseEntity.badRequest().body(body);
        }
    }
}
