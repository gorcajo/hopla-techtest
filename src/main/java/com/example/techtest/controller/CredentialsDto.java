package com.example.techtest.controller;

public class CredentialsDto {

    private final String username;
    private final String password;

    public CredentialsDto() {
        this.username = null;
        this.password = null;
    }

    public CredentialsDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
