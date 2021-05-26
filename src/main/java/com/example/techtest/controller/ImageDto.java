package com.example.techtest.controller;

public class ImageDto {

    private final String name;
    private final String base64;

    public ImageDto() {
        this.name = null;
        this.base64 = null;
    }

    public ImageDto(String name, String base64) {
        this.name = name;
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public String getBase64() {
        return base64;
    }
}
