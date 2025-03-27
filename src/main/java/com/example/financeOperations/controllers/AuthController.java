package com.example.financeOperations.controllers;

import com.example.financeOperations.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.financeOperations.services.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        System.out.println(user.toString());
        try {
            User savedUser = authService.login(user);
            return ResponseEntity.ok(savedUser);
        }
        catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Ошибка" + e.getMessage());
        }
    }
}
