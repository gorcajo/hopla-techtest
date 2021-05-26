package com.example.techtest.exception;

public class IllegalArgumentsException extends RuntimeException {

    public IllegalArgumentsException() {
        super();
    }

    public IllegalArgumentsException(String errorMessage) {
        super(errorMessage);
    }
}
