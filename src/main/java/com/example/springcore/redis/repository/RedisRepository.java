package com.example.springcore.redis.repository;

import com.example.springcore.redis.model.LoginAttemptModel;
import com.example.springcore.redis.model.RedisTokenModel;

public interface RedisRepository {
    void saveUserToken(String username, RedisTokenModel redisTokenModel);

    RedisTokenModel findUserTokenByUsername(String username);

    void updateUserToken(String username, RedisTokenModel redisTokenModel);

    void deleteUserToken(String username);

    void saveLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel);

    LoginAttemptModel findLoginAttemptByIpAddress(String ipAddress);

    void updateLoginAttempt(String ipAddress, LoginAttemptModel loginAttemptModel);

    void deleteLoginAttempt(String ipAddress);
}
