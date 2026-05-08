package com.example.backend.model;

import java.io.Serializable;

public class AuthRequest implements Serializable {
    private String username;
    private String password;

    public AuthRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}