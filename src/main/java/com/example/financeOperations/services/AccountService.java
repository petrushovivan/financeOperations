package com.example.financeOperations.services;

import com.example.financeOperations.models.Account;
import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.AccountRepository;
import com.example.financeOperations.repositories.UserRepository;
import com.example.financeOperations.security.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, JWTUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> getAccounts(HttpServletRequest request) {
        String token = jwtUtils.getToken(request);
        if (token != null && jwtUtils.validateJWT(token)) {

            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                List<Account> accounts = user.getAccounts();
                Map<String, Object> body = new HashMap<>();
                body.put("message", "Accounts successfully received");
                body.put("status", true);
                body.put("accounts", accounts);
                return ResponseEntity.ok(body);
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error in getting accounts");
        body.put("status", false);
        return ResponseEntity.badRequest().body(body);
    }
}
