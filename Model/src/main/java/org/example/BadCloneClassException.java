package org.example;

public class BadCloneClassException extends CloneNotSupportedException {
    public BadCloneClassException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
