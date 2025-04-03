package com.example.financeOperations.controllers;

import com.example.financeOperations.exeption.UserAlreadyExistException;
import com.example.financeOperations.exeption.UserNotExistException;
import com.example.financeOperations.exeption.enums.ExceptionName;
import com.example.financeOperations.models.User;
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
    public ResponseEntity<?> signup(@RequestBody User user) {
        System.out.println(user.toString());
        try {
            User savedUser = authService.register(user);
            return ResponseEntity.ok(savedUser);
        }
        catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(ExceptionName.USERALREADYEXIST, HttpStatus.BAD_REQUEST);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User userToLogin = authService.login(user);
            return ResponseEntity.ok(userToLogin);
        }
        catch (UserNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
