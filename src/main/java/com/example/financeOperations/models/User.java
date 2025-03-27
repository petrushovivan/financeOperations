package com.example.financeOperations.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
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
    @Column(name = "passwordHash")
    private int passwordHash;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    public User(String password, String email, String username, int id) {
        this.passwordHash = Objects.hash(password);
        this.email = email;
        this.username = username;
        this.id = id;
    }

    public void setPassword(String password) {
        this.passwordHash = Objects.hash(password);
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
