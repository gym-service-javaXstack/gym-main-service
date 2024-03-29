package com.example.springcore.handler;

import com.example.springcore.exceptions.BruteForceProtectorException;
import com.example.springcore.exceptions.Error;
import com.example.springcore.exceptions.ErrorType;
import com.example.springcore.exceptions.UserNotAuthenticatedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("handleEntityNotFoundException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.DATABASE_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("handleIllegalArgumentException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("handleBadCredentialsException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.VALIDATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        log.error("handleUserNotAuthenticatedException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.AUTHORIZATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("handleExpiredJwtException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.AUTHORIZATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleJwtException(JwtException ex) {
        log.error("handleJwtException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.AUTHORIZATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BruteForceProtectorException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ResponseEntity<Error> handleBruteForceProtectorException(BruteForceProtectorException ex) {
        log.error("handleBruteForceProtectorException: message: {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.PROCESSING_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException: message: {}", ex.getMessage(), ex);
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
    public ResponseEntity<Error> handleException(Exception ex) {
        log.error("handleException: message {}", ex.getMessage(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }
}
