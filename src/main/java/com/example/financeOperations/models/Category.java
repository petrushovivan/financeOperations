package com.example.financeOperations.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @OneToOne(mappedBy = "category")
    private Transaction transaction;

    public Category() {
    }

    public Category(int id, String name, String type, Transaction transaction) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.transaction = transaction;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
