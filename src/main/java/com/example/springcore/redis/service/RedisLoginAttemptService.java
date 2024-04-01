package com.example.springcore.redis.service;

import com.example.springcore.redis.model.RedisLoginAttemptModel;
import com.example.springcore.redis.repository.RedisLoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisLoginAttemptService {
    private final RedisLoginAttemptRepository redisLoginAttemptRepository;

    public void saveLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        redisLoginAttemptRepository.saveLoginAttempt(ipAddress, redisLoginAttemptModel);
    }

    public Optional<RedisLoginAttemptModel> findLoginAttemptByIpAddress(String ipAddress) {
        return redisLoginAttemptRepository.findLoginAttemptByIpAddress(ipAddress);
    }


    public void updateLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        redisLoginAttemptRepository.updateLoginAttempt(ipAddress, redisLoginAttemptModel);
    }

    public void deleteLoginAttempt(String ipAddress) {
        redisLoginAttemptRepository.deleteLoginAttempt(ipAddress);
    }
}
