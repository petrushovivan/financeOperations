package com.example.financeOperations.services;

import com.example.financeOperations.models.Account;
import com.example.financeOperations.models.Category;
import com.example.financeOperations.models.DTOS.CategoryDTO;
import com.example.financeOperations.models.DTOS.TransactionDTO;
import com.example.financeOperations.models.Transaction;
import com.example.financeOperations.models.User;
import com.example.financeOperations.repositories.TransactionRepository;
import com.example.financeOperations.repositories.UserRepository;
import com.example.financeOperations.security.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(JWTUtils jwtUtils, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<?> getTransactions(HttpServletRequest request, String accountName) {
        String token = jwtUtils.getToken(request);
        if(token != null && jwtUtils.validateJWT(token)) {
            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if(user != null) {
                Account account = user.getAccounts().stream().
                        filter(a -> a.getName().equals(accountName)).findFirst().orElse(null);
                if(account != null) {
                    List<Transaction> transactions = account.getTransactions();
                    List<TransactionDTO> transactionDTOS = new ArrayList<>();
                    for(Transaction t : transactions) {
                        TransactionDTO dto = new TransactionDTO(t.getAmount(), t.getDescription(), t.getDate(), new CategoryDTO());
                        dto.getCategory().setName(t.getCategory().getName());
                        dto.getCategory().setType(t.getCategory().getType());
                        transactionDTOS.add(dto);
                    }
                    Map<String, Object> body = new HashMap<>();
                    body.put("your transactions", transactionDTOS);
                    body.put("status", true);
                    return ResponseEntity.ok(body);
                }
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", "error in getting account");
        body.put("status", true);
        return ResponseEntity.badRequest().body(body);
    }

    public ResponseEntity<?> addTransaction(HttpServletRequest request, TransactionDTO transactionDTO, String accountName) {
        String token = jwtUtils.getToken(request);
        if(token != null && jwtUtils.validateJWT(token)) {
            String email = jwtUtils.getEmailFromJWT(token);
            User user = userRepository.findUserByEmail(email);
            if(user != null) {
                Account account = user.getAccounts().stream().
                        filter(a -> a.getName().equals(accountName)).findFirst().orElse(null);
                if(account != null) {
                    Transaction transaction = new Transaction();
                    Category c = new Category(transactionDTO.getCategory().getName(), transaction.getCategory().getType());
                    transaction.setCategory(c);
                    transaction.setDate(transactionDTO.getDate());
                    transaction.setAmount(transactionDTO.getAmount());
                    transaction.setDescription(transactionDTO.getDescription());
                    account.getTransactions().add(transaction);
                    user.getAccounts().add(account);
                    userRepository.save(user);

                    Map<String, Object> body = new HashMap<>();
                    body.put("message", "Account added successfully");
                    body.put("status", true);
                    return ResponseEntity.badRequest().body(body);
                }
            }
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", "error in adding transaction");
        body.put("status", true);
        return ResponseEntity.badRequest().body(body);
    }
}
