package com.example;

public class NotANumberException extends IntroSceneException {
    public NotANumberException(String message, Throwable cause) {
        super(message);
        super.initCause(cause);
    }

}
