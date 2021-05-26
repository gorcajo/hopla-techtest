package com.example.techtest.controller;

public class TicketCreationRequestDto {

    private final int desiredImageCount;

    public TicketCreationRequestDto() {
        this.desiredImageCount = 0;
    }

    public TicketCreationRequestDto(int desiredImageCount) {
        this.desiredImageCount = desiredImageCount;
    }

    public int getDesiredImageCount() {
        return desiredImageCount;
    }
}
