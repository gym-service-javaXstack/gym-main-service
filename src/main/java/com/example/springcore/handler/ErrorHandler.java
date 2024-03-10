package com.example.springcore.handler;

import com.example.springcore.exceptions.Error;
import com.example.springcore.exceptions.ErrorType;
import com.example.springcore.exceptions.UserNotAuthenticatedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Entity not found", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))
            })
    })
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException ex, HandlerMethod hm) {
        log.error("handleEntityNotFoundException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.DATABASE_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))
            })
    })
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException ex, HandlerMethod hm) {
        log.error("handleIllegalArgumentException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.DATABASE_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Unauthorized ", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))
            })
    })
    public ResponseEntity<Error> handUserNotAuthenticatedException(UserNotAuthenticatedException ex, HandlerMethod hm) {
        log.error("handUserNotAuthenticatedException: message: {}, method: {}", ex.getStackTrace(), hm.getMethod().getName());
        Error error = new Error(ex.getMessage(), ErrorType.AUTHORIZATION_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Server error", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error.class))
            })
    })
    public ResponseEntity<Error> handleException(Exception ex, HandlerMethod hm) {
        log.error("handleException: message {}, method {}", ex.getMessage(), hm.getMethod().getName(), ex);
        Error error = new Error(ex.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
        return ResponseEntity.internalServerError().body(error);
    }
}
