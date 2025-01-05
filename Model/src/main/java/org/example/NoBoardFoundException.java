package org.example;

public class NoBoardFoundException extends DatabaseException {
    public NoBoardFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
