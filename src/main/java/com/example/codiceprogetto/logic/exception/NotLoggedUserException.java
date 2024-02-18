package com.example.codiceprogetto.logic.exception;

public class NotLoggedUserException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotLoggedUserException(String message) {
        super(message);
    }
}
