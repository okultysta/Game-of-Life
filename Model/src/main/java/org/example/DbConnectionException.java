package org.example;

public class DbConnectionException extends DatabaseException {
    public DbConnectionException(String message,Throwable cause) {
        super(message, cause);
    }
}
