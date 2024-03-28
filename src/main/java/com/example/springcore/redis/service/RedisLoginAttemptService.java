package com.example.springcore.redis.service;

import com.example.springcore.redis.model.LoginAttemptModel;
import com.example.springcore.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLoginAttemptService {
    private final RedisRepository redisRepository;

    public void saveLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel) {
        redisRepository.saveLoginAttempt(ipAddress, loginAttemptModel);
    }


    public LoginAttemptModel findLoginAttemptByIpAddress(String ipAddress) {
        return redisRepository.findLoginAttemptByIpAddress(ipAddress);
    }


    public void updateLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel) {
        redisRepository.updateLoginAttempt(ipAddress, loginAttemptModel);
    }

    public void deleteLoginAttempt(String ipAddress) {
        redisRepository.deleteLoginAttempt(ipAddress);
    }
}
