package com.example.techtest.controller;

import java.util.UUID;

public class TokenDto {

    private final String token;

    public TokenDto() {
        this.token = null;
    }

    public TokenDto(UUID token) {
        this.token = token.toString();
    }

    public String getToken() {
        return token;
    }
}
