package com.example.springcore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private Map<String, Integer> usernameCount = new HashMap<>();
    private Random random = new Random();
    private final String CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        Integer count = usernameCount.getOrDefault(baseUsername, 0);
        usernameCount.put(baseUsername, count + 1);
        String username = count > 0 ? baseUsername + count : baseUsername;
        logger.debug("Generated username: {}", username);
        return username;
    }

    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        String generatedPassword = password.toString();
        logger.debug("Generated password");
        return generatedPassword;
    }
}
