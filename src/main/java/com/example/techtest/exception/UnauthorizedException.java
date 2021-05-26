package com.example.techtest.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
