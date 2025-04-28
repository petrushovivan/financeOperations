package com.example.financeOperations.models.DTOS;

import java.util.Date;

public class TransactionDTO {
    private double amount;

    private String description;

    private Date date;

    private CategoryDTO category;

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public TransactionDTO(double amount, String description, Date date, CategoryDTO category) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public TransactionDTO() {
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
