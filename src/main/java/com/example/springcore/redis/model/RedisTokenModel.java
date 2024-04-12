package com.example.springcore.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("RedisTokenModel")
@AllArgsConstructor
public class RedisTokenModel implements Serializable {
    private String token;
}
