package com.example;

public class InvalidSceneFileException extends RuntimeException {
    public InvalidSceneFileException(String message, Throwable cause) {
        super(message);
        super.initCause(cause);
    }
}
