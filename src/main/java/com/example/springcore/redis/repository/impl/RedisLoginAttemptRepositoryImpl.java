package com.example.springcore.redis.repository.impl;

import com.example.springcore.redis.model.RedisLoginAttemptModel;
import com.example.springcore.redis.repository.RedisLoginAttemptRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisLoginAttemptRepositoryImpl implements RedisLoginAttemptRepository {
    private static final String LOGIN_ATTEMPT_MODEL_KEY = "RedisLoginAttemptModel";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, RedisLoginAttemptModel> loginAttemptHashOperations;

    public RedisLoginAttemptRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.loginAttemptHashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        loginAttemptHashOperations.put(LOGIN_ATTEMPT_MODEL_KEY, ipAddress, redisLoginAttemptModel);
    }

    @Override
    public Optional<RedisLoginAttemptModel> findLoginAttemptByIpAddress(String ipAddress) {
        RedisLoginAttemptModel redisLoginAttemptModel = loginAttemptHashOperations.get(LOGIN_ATTEMPT_MODEL_KEY, ipAddress);
        return Optional.ofNullable(redisLoginAttemptModel);
    }

    @Override
    public void updateLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel) {
        loginAttemptHashOperations.put(LOGIN_ATTEMPT_MODEL_KEY, ipAddress, redisLoginAttemptModel);
    }

    @Override
    public void deleteLoginAttempt(String ipAddress) {
        loginAttemptHashOperations.delete(LOGIN_ATTEMPT_MODEL_KEY, ipAddress);
    }
}
