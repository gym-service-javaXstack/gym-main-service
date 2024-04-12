package com.example.springcore.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorType errorType = ErrorType.FATAL_ERROR;

    ServiceException(String message) {
        super(message);
    }
}