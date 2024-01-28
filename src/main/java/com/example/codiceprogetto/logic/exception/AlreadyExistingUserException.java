package com.example.codiceprogetto.logic.exception;

public class AlreadyExistingUserException extends Exception{
    private static final long serialVersionUID = 1L;

    public AlreadyExistingUserException(String message) {
        super(message);
    }
}
