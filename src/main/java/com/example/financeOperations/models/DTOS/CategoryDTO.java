package com.example.financeOperations.models.DTOS;


public class CategoryDTO {

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
