package org.example;

public class DatabaseException extends DaoException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
