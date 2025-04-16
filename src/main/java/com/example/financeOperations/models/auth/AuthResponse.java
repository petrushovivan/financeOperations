package com.example.financeOperations.models.auth;

import java.util.List;

public class AuthResponse {
    private String username;
    private String email;
    private String jwtToken;
    private List<String> roles;

    public AuthResponse(String username, String email, String jwtToken, List<String> roles) {
        this.email = email;
        this.username = username;
        this.jwtToken = jwtToken;
        this.roles = roles;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public List<String> getRoles() {
        return roles;
    }
}
