package com.example;

public class InvalidResourcesException extends Exception {
    public InvalidResourcesException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
