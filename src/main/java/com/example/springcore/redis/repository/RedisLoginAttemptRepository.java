package com.example.springcore.redis.repository;

import com.example.springcore.redis.model.RedisLoginAttemptModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisLoginAttemptRepository {
    void saveLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel);

    Optional<RedisLoginAttemptModel> findLoginAttemptByIpAddress(String ipAddress);

    void updateLoginAttempt(String ipAddress, RedisLoginAttemptModel redisLoginAttemptModel);

    void deleteLoginAttempt(String ipAddress);
}
