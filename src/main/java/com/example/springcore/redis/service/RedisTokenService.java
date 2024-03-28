package com.example.springcore.redis.service;

import com.example.springcore.redis.model.RedisTokenModel;
import com.example.springcore.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisRepository redisRepository;

    public void saveUserToken(String username, RedisTokenModel redisTokenModel) {
        redisRepository.saveUserToken(username, redisTokenModel);
    }

    public RedisTokenModel findUserTokenByUsername(String username) {
        return redisRepository.findUserTokenByUsername(username);
    }

    public void updateUserToken(String username, RedisTokenModel redisTokenModel) {
        redisRepository.updateUserToken(username, redisTokenModel);
    }

    public void deleteUserToken(String username) {
        redisRepository.deleteUserToken(username);
    }
}
