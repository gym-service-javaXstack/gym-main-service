package com.example.springcore.exceptions;

import java.time.LocalDateTime;

public record Error(String message, ErrorType errorType, LocalDateTime timeStamp) {
}