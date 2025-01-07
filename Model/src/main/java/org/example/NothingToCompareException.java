package org.example;

public class NothingToCompareException extends RuntimeException{
    public NothingToCompareException(String message, Throwable cause) {
        super(message, cause);
    }
}
