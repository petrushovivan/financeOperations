package com.example.financeOperations.services;

import com.example.financeOperations.models.User;
import com.example.financeOperations.models.auth.AuthRequest;
import com.example.financeOperations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            body.put("message", "login successful");
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

        }
        else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "incorrect credentials");
            body.put("status", false);
            return ResponseEntity.badRequest().body(body);
        }
    }
}
