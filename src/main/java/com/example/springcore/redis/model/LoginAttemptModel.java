package com.example.springcore.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RedisHash("LoginAttemptModel")
@AllArgsConstructor
public class LoginAttemptModel implements Serializable {
    private int attempts;
    private LocalDateTime lastAttempt;
}