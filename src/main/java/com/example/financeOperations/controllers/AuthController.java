package com.example.financeOperations.controllers;

import com.example.financeOperations.exeption.UserAlreadyExistException;
import com.example.financeOperations.exeption.enums.ExceptionName;
import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> signup(@RequestBody(required = false) User user) {
        System.out.println(user.toString());
        try {
            User savedUser = authService.login(user);
            return ResponseEntity.ok(savedUser);
        }
        catch (UserAlreadyExistException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(ExceptionName.USERALREADYEXIST, HttpStatus.BAD_REQUEST);
        }
        catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(user);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        System.out.println(user.toString());
        try {
            return ResponseEntity.badRequest().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(user);
        }
    }
}
