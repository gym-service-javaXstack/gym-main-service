package com.example.springcore.redis.repository.impl;

import com.example.springcore.redis.model.RedisTokenModel;
import com.example.springcore.redis.repository.RedisTokenRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisTokenRepositoryImpl implements RedisTokenRepository {
    private static final String REDIS_TOKEN_MODEL_KEY = "RedisTokenModel";

    private final HashOperations<String, String, RedisTokenModel> tokenHashOperations;

    public RedisTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.tokenHashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveUserToken(String username, RedisTokenModel redisTokenModel) {
        tokenHashOperations.put(REDIS_TOKEN_MODEL_KEY, username, redisTokenModel);
    }

    @Override
    public Optional<RedisTokenModel> findUserTokenByUsername(String username) {
        RedisTokenModel tokenModel = tokenHashOperations.get(REDIS_TOKEN_MODEL_KEY, username);
        return Optional.ofNullable(tokenModel);
    }

    @Override
    public void updateUserToken(String username, RedisTokenModel redisTokenModel) {
        tokenHashOperations.put(REDIS_TOKEN_MODEL_KEY, username, redisTokenModel);
    }

    @Override
    public void deleteUserToken(String username) {
        tokenHashOperations.delete(REDIS_TOKEN_MODEL_KEY, username);
    }
}
