package org.example.exceptions;

public class BadCloneClassException extends CloneNotSupportedException {
    public BadCloneClassException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
