package com.example.financeOperations.controllers;

import com.example.financeOperations.models.DTOS.TransactionDTO;
import com.example.financeOperations.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions/{accountName}")
    public ResponseEntity<?> getTransactions(@PathVariable String accountName, HttpServletRequest request) {
        return transactionService.getTransactions(request ,accountName);
    }

    @PostMapping("/add/{accountName}")
    public ResponseEntity<?> addTransaction(@PathVariable String accountName, @RequestBody TransactionDTO transactionDTO, HttpServletRequest request) {
        return transactionService.addTransaction(request, transactionDTO, accountName);
    }
}
