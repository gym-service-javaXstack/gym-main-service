package com.example.springcore.redis.service;

import com.example.springcore.redis.model.RedisLoginAttemptModel;
import com.example.springcore.redis.repository.RedisLoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisLoginAttemptService {
    private final RedisLoginAttemptRepository redisRepository;

    public void saveLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        redisRepository.saveLoginAttempt(ipAddress, redisLoginAttemptModel);
    }

    public Optional<RedisLoginAttemptModel> findLoginAttemptByIpAddress(String ipAddress) {
        return redisRepository.findLoginAttemptByIpAddress(ipAddress);
    }


    public void updateLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        redisRepository.updateLoginAttempt(ipAddress, redisLoginAttemptModel);
    }

    public void deleteLoginAttempt(String ipAddress) {
        redisRepository.deleteLoginAttempt(ipAddress);
    }
}
