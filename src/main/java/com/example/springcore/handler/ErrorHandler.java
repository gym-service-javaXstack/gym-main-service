package com.example.springcore.handler;

import com.example.springcore.exceptions.Error;
import com.example.springcore.exceptions.ErrorType;
import com.example.springcore.exceptions.UserNotAuthenticatedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException ex, HandlerMethod hm) {
        log.error("handleEntityNotFoundException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.DATABASE_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex, HandlerMethod hm) {
        log.error("handleIllegalArgumentException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handUserNotAuthenticatedException(UserNotAuthenticatedException ex, HandlerMethod hm) {
        log.error("handUserNotAuthenticatedException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.AUTHORIZATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handMethodArgumentNotValidException(MethodArgumentNotValidException ex, HandlerMethod hm) {
        log.error("handMethodArgumentNotValidException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        Error error = new Error(errors.toString(), ErrorType.VALIDATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleException(Exception ex, HandlerMethod hm) {
        log.error("handleException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }
}
