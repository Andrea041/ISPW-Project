package com.example.codiceprogetto.logic.exception;

public class AlreadyAppliedCouponException extends Exception {
    private static final long serialVersionUID = 1L;

    public AlreadyAppliedCouponException(String message) {
        super(message);
    }
}
