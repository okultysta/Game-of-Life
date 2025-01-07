package com.example;

public class PropertyBuildException extends Exception {
    public PropertyBuildException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
