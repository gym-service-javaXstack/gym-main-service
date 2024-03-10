package com.example.springcore.exceptions;

import java.io.Serial;

public class UserNotAuthenticatedException extends ServiceException {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "User not authorized";

    public UserNotAuthenticatedException() {
        this(DEFAULT_MESSAGE);
    }

    public UserNotAuthenticatedException(String message) {
        super(message);
        setErrorType(ErrorType.AUTHORIZATION_ERROR);
    }
}
