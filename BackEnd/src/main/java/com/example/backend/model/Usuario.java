package com.example.backend.model;

import java.io.Serializable;
import java.util.UUID;

public class Usuario implements Serializable {
    private String id;
    private String username;
    private String password;
    private String rol;

    public Usuario() {}

    public Usuario(String username, String password, String rol) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}