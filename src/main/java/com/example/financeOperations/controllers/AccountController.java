package com.example.financeOperations.controllers;

import com.example.financeOperations.models.accountsEntity.AccountDTO;
import com.example.financeOperations.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getAccounts(HttpServletRequest request) {
        return accountService.getAccounts(request);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getAccountByName(HttpServletRequest request, @PathVariable String name) {
        return accountService.findByName(request, name);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(HttpServletRequest request, @RequestBody AccountDTO accountToAdd) {
        return accountService.addAccount(request, accountToAdd);
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> patchAccount(HttpServletRequest request, @RequestBody AccountDTO accountDTO) {
        return accountService.patchAccount(request, accountDTO);
    }
}
