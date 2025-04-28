package com.example.financeOperations.services;

import com.example.financeOperations.models.Account;
import com.example.financeOperations.models.User;
import com.example.financeOperations.models.DTOS.AccountDTO;
import com.example.financeOperations.repositories.UserRepository;
import com.example.financeOperations.security.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(UserRepository userRepository, JWTUtils jwtUtils) {
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
                List<AccountDTO> accountDTOS = new ArrayList<>();

                for (Account a : accounts) {
                    AccountDTO dto = new AccountDTO(a.getName(), a.getBalance(), a.getCurrency());
                    accountDTOS.add(dto);
                }

                Map<String, Object> body = new HashMap<>();
                body.put("message", "Accounts successfully received");
                body.put("status", true);
                body.put("accounts", accountDTOS);
                return ResponseEntity.ok(body);
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error in getting accounts");
        body.put("status", false);
        return ResponseEntity.badRequest().body(body);
    }

    public ResponseEntity<?> addAccount(HttpServletRequest request, AccountDTO accountDTO) {
        String token = jwtUtils.getToken(request);

        if (token != null && jwtUtils.validateJWT(token)) {

            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if (user != null) {

                Account existedAccount = user.getAccounts().stream().
                        filter(a -> a.getName().equals(accountDTO.getName())).findFirst().orElse(null);
                if (existedAccount != null) {

                    Account accountToAdd = new Account();
                    accountToAdd.setName(accountDTO.getName());
                    accountToAdd.setCurrency(accountDTO.getCurrency());
                    accountToAdd.setBalance(accountDTO.getBalance());
                    accountToAdd.setActive(true);
                    accountToAdd.setUser(user);
                    user.addAccount(accountToAdd);

                    userRepository.save(user);

                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "Account added successful");
                    body.put("status", true);
                    return ResponseEntity.ok().body(body);
                }
                else {
                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "Account already exist");
                    body.put("status", false);
                    return ResponseEntity.badRequest().body(body);
                }
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error adding account");
        body.put("status", false);
        return ResponseEntity.badRequest().body(body);
    }

    public ResponseEntity<?> patchAccount(HttpServletRequest request, AccountDTO accountDTO) {
        String token = jwtUtils.getToken(request);
        logger.debug("try to validate user");

        if (token != null && jwtUtils.validateJWT(token)) {
            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                logger.debug("user not null");
                List<Account> accounts = user.getAccounts();
                Account accountToChange = accounts.stream().filter(a -> a.getName().equals(accountDTO.getName()))
                        .findAny().orElse(null);
                if (accountToChange != null) {
                    logger.debug("account not null");
                    accountToChange.setBalance(accountDTO.getBalance());
                    accountToChange.setCurrency(accountDTO.getCurrency());
                    userRepository.save(user);

                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "account changed successful");
                    body.put("staus", true);
                    return ResponseEntity.ok().body(body);
                }
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "account not changed");
        body.put("status", false);
        return ResponseEntity.badRequest().body(body);
    }

    public ResponseEntity<?> findByName(HttpServletRequest request, String name) {
        String token = jwtUtils.getToken(request);

        if (token != null && jwtUtils.validateJWT(token)) {
            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if (user != null) {
                List<Account> accounts = user.getAccounts();
                Account requestedAccount = accounts.stream().filter(a -> a.getName().equals(name))
                        .findFirst().orElse(null);
                if(requestedAccount != null) {
                    AccountDTO accountDTO =
                            new AccountDTO(requestedAccount.getName(), requestedAccount.getBalance(), requestedAccount.getCurrency());
                    Map<String, Object> body = new HashMap<>();
                    body.put("requested account", accountDTO);
                    body.put("status", true);
                    return ResponseEntity.ok(body);
                }
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", "account not found");
        body.put("status", false);
        return ResponseEntity.badRequest().body(body);
    }
}
