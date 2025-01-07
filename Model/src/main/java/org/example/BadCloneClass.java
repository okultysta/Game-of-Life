package org.example;

public class BadCloneClass extends CloneNotSupportedException {
    public BadCloneClass(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
