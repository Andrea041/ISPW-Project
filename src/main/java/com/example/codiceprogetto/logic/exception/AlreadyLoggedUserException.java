package com.example.codiceprogetto.logic.exception;

public class AlreadyLoggedUserException extends Exception{
    private static final long serialVersionUID = 1L;

    public AlreadyLoggedUserException(String message) {
        super(message);
    }
}
