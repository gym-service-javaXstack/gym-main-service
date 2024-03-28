package com.example.springcore.util;

import com.example.springcore.redis.model.LoginAttemptModel;
import com.example.springcore.redis.service.RedisLoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BruteForceProtectorService {
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_DURATION_MINUTES = 5;

    private final RedisLoginAttemptService redisLoginAttemptService;


    public boolean isBlocked(String ipAddress) {
        LoginAttemptModel attempt = redisLoginAttemptService.findLoginAttemptByIpAddress(ipAddress);
        if (attempt == null) {
            return false;
        }

        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            if (attempt.getLastAttempt().plusMinutes(BLOCK_DURATION_MINUTES).isAfter(LocalDateTime.now())) {
                return true;
            } else {
                redisLoginAttemptService.deleteLoginAttempt(ipAddress);
            }
        }

        return false;
    }

    public void registerFailedAttempt(String ipAddress) {
        LoginAttemptModel attempt = redisLoginAttemptService.findLoginAttemptByIpAddress(ipAddress);
        if (attempt == null) {
            attempt = new LoginAttemptModel(0, LocalDateTime.now());
        }
        attempt.setAttempts(attempt.getAttempts() + 1);
        attempt.setLastAttempt(LocalDateTime.now());
        redisLoginAttemptService.saveLoginAttempt(ipAddress, attempt);
    }

    public void resetAttempts(String ipAddress) {
        redisLoginAttemptService.deleteLoginAttempt(ipAddress);
    }
}
