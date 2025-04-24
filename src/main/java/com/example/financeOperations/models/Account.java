package com.example.financeOperations.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "currency")
    private String currency;

    public Account() {
    }

    public Account(int id, String name, double balance, User user, List<Transaction> transactions, boolean isActive, String currency) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.user = user;
        this.transactions = transactions;
        this.isActive = isActive;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getCurrency() {
        return currency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
