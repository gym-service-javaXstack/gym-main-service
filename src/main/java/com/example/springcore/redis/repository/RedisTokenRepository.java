package com.example.springcore.redis.repository;

import com.example.springcore.redis.model.RedisTokenModel;

import java.util.Optional;

public interface RedisTokenRepository {
    void saveUserToken(String username, RedisTokenModel redisTokenModel);

    Optional<RedisTokenModel> findUserTokenByUsername(String username);

    void updateUserToken(String username, RedisTokenModel redisTokenModel);

    void deleteUserToken(String username);
}
