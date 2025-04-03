package com.example.financeOperations.services;

import com.example.financeOperations.exeption.UserAlreadyExistException;
import com.example.financeOperations.exeption.enums.ExceptionName;
import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) throws UserAlreadyExistException{
        this.userRepository = userRepository;
    }
    public User login(User user) {
        User existedUser = userRepository.findAll().stream().
                filter(u -> u.getEmail().equals(user.getEmail())).findFirst().orElse(null);
        if(existedUser != null) {
            throw new UserAlreadyExistException(ExceptionName.USERALREADYEXIST.toString());
        }
        return userRepository.save(user);
    }
}
