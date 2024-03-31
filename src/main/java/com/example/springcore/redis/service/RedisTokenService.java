package com.example.springcore.redis.service;

import com.example.springcore.redis.model.RedisTokenModel;
import com.example.springcore.redis.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisTokenService {
    private final RedisTokenRepository redisRepository;

    public void saveUserToken(String username, RedisTokenModel redisTokenModel) {
        redisRepository.saveUserToken(username, redisTokenModel);
    }

    public Optional<RedisTokenModel> findUserTokenByUsername(String username) {
        return redisRepository.findUserTokenByUsername(username);
    }

    public void updateUserToken(String username, RedisTokenModel redisTokenModel) {
        redisRepository.updateUserToken(username, redisTokenModel);
    }

    public void deleteUserToken(String username) {
        redisRepository.deleteUserToken(username);
    }
}
