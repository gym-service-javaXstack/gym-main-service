package com.example.springcore.redis.repository;

import com.example.springcore.redis.model.LoginAttemptModel;
import com.example.springcore.redis.model.RedisTokenModel;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private static final String REDIS_TOKEN_MODEL_KEY = "RedisTokenModel";
    private static final String LOGIN_ATTEMPT_MODEL_KEY = "LoginAttemptModel";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, RedisTokenModel> tokenHashOperations;
    private HashOperations<String, String, LoginAttemptModel> loginAttemptHashOperations;

    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.tokenHashOperations = redisTemplate.opsForHash();
        this.loginAttemptHashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveUserToken(String username, RedisTokenModel redisTokenModel) {
        tokenHashOperations.put(REDIS_TOKEN_MODEL_KEY, username, redisTokenModel);
    }

    @Override
    public RedisTokenModel findUserTokenByUsername(String username) {
        return tokenHashOperations.get(REDIS_TOKEN_MODEL_KEY, username);
    }

    @Override
    public void updateUserToken(String username, RedisTokenModel redisTokenModel) {
        tokenHashOperations.put(REDIS_TOKEN_MODEL_KEY, username, redisTokenModel);
    }

    @Override
    public void deleteUserToken(String username) {
        tokenHashOperations.delete(REDIS_TOKEN_MODEL_KEY, username);
    }

    @Override
    public void saveLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel) {
        loginAttemptHashOperations.put(LOGIN_ATTEMPT_MODEL_KEY, ipAddress, loginAttemptModel);
    }

    @Override
    public LoginAttemptModel findLoginAttemptByIpAddress(String ipAddress) {
        return loginAttemptHashOperations.get(LOGIN_ATTEMPT_MODEL_KEY, ipAddress);
    }

    @Override
    public void updateLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel) {
        loginAttemptHashOperations.put(LOGIN_ATTEMPT_MODEL_KEY, ipAddress, loginAttemptModel);
    }

    @Override
    public void deleteLoginAttempt(String ipAddress) {
        loginAttemptHashOperations.delete(LOGIN_ATTEMPT_MODEL_KEY, ipAddress);
    }
}
