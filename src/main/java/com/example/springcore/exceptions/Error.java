package com.example.springcore.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record Error(@Schema(description = "Error message", example = "message") String message,
                    @Schema(description = "Error type", example = "Type based on error") ErrorType errorType,
                    LocalDateTime timeStamp) {
}