package com.example.springcore.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ProfileService {
    private Map<String, Integer> usernameCount = new HashMap<>();
    private Random random = new Random();
    private final String CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        Integer count = usernameCount.getOrDefault(baseUsername, 0);
        usernameCount.put(baseUsername, count + 1);
        return count > 0 ? baseUsername + count : baseUsername;
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return password.toString();
    }
}
