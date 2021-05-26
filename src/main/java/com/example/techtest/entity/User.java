package com.example.techtest.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String token;

    public User() {
    }

    public User(String username, String password, UUID token) {
        this.username = username;
        this.password = password;
        this.token = token.toString();
    }

    public User(long id, String username, String password, UUID token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getToken() {
        return UUID.fromString(token);
    }

    public void setToken(UUID token) {
        this.token = token.toString();
    }
}
