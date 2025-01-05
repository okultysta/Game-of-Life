package org.example;

public class DbReadWriteException extends DatabaseException {
    public DbReadWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
