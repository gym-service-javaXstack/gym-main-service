package com.example.springcore.exceptions;

import java.io.Serial;

public class BruteForceProtectorException extends ServiceException{
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Too many failed login attempts. Please try again later.";

    public BruteForceProtectorException() {
        this(DEFAULT_MESSAGE);
    }

    public BruteForceProtectorException(int blockDurationMinutes) {
        this("Too many failed login attempts. Please try again later. After " + blockDurationMinutes + " min.");
    }

    public BruteForceProtectorException(String message) {
        super(message);
        setErrorType(ErrorType.AUTHORIZATION_ERROR);
    }
}
