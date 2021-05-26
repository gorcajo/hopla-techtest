package com.example.techtest.exception;

public class ConflictException extends RuntimeException {

    public ConflictException() {
        super();
    }

    public ConflictException(String errorMessage) {
        super(errorMessage);
    }
}
