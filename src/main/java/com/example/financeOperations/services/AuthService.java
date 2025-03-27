package com.example.financeOperations.services;

import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User login(User user) {
        return userRepository.save(user);
    }
}
