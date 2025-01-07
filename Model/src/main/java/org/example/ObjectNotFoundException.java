package org.example;

public class ObjectNotFoundException extends DatabaseException {
    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
