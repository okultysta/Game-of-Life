package org.example;

public class DaoException extends Exception {
    public DaoException(String message, Throwable cause) {
        super(message);
        super.initCause(cause);
    }
}
