package com.example.financeOperations.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private int passwordHash;
    @Column(name = "token")
    private String token;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public User(String username, String email, String password) {
        this.passwordHash = Objects.hash(password);
        this.email = email;
        this.username = username;
    }

    public User() {}

    public void setPassword(String password) {
        this.passwordHash = Objects.hash(password);
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return passwordHash == user.passwordHash && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash=" + passwordHash +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
