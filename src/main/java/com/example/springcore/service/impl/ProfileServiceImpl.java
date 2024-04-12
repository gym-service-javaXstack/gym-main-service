package com.example.springcore.service.impl;

import com.example.springcore.service.ProfileService;
import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final Random random = new Random();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        List<String> existingUsernames = userService.getUsernameByFirstNameAndLastName(firstName, lastName);

        boolean isContains = existingUsernames.contains(baseUsername);

        if (!isContains) {
            return baseUsername;
        }

        Integer count = existingUsernames.stream()
                .map(username -> username.replace(baseUsername, ""))
                .filter(suffix -> suffix.matches("\\d+"))
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElse(0);

        count++;
        String username = count > 0 ? baseUsername + count : baseUsername;
        log.debug("Generated username: {}", username);
        return username;
    }

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        String generatedPassword = password.toString();
        log.debug("Generated password");
        return generatedPassword;
    }
}
