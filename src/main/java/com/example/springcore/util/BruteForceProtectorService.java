package com.example.springcore.util;

import com.example.springcore.redis.model.LoginAttemptModel;
import com.example.springcore.redis.service.RedisLoginAttemptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class BruteForceProtectorService {

    @Value("${bruteforce.max-attempts:3}")
    private int MAX_ATTEMPTS;

    @Value("${bruteforce.block-duration-minutes:5}")
    private int BLOCK_DURATION_MINUTES;

    private final RedisLoginAttemptService redisLoginAttemptService;


    public boolean isBlocked(String ipAddress) {
        log.info("Entry BruteForceProtectorService isBlocked method: {}", ipAddress);
        LoginAttemptModel attempt = redisLoginAttemptService.findLoginAttemptByIpAddress(ipAddress);
        if (attempt == null) {
            return false;
        }

        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            if (attempt.getLastAttempt().plusMinutes(BLOCK_DURATION_MINUTES).isAfter(LocalDateTime.now())) {
                log.info("Exit BruteForceProtectorService isBlocked method: attempts {}", attempt.getAttempts());
                return true;
            } else {
                redisLoginAttemptService.deleteLoginAttempt(ipAddress);
            }
        }
        log.info("Exit BruteForceProtectorService isBlocked method: {}", ipAddress);
        return false;
    }

    public void registerFailedAttempt(String ipAddress) {
        log.info("Entry BruteForceProtectorService registerFailedAttempt method: {}", ipAddress);
        LoginAttemptModel attempt = redisLoginAttemptService.findLoginAttemptByIpAddress(ipAddress);
        if (attempt == null) {
            attempt = new LoginAttemptModel(0, LocalDateTime.now());
        }
        attempt.setAttempts(attempt.getAttempts() + 1);
        attempt.setLastAttempt(LocalDateTime.now());
        redisLoginAttemptService.saveLoginAttempt(ipAddress, attempt);
        log.info("Exit BruteForceProtectorService registerFailedAttempt method: attempt count {}", attempt);
    }

    public void resetAttempts(String ipAddress) {
        redisLoginAttemptService.deleteLoginAttempt(ipAddress);
    }
}
