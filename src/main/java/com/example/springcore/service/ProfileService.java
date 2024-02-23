package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.ParentUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
public class ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private Random random = new Random();
    private final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final ParentUserDao parentUserDao;

    private Map<String, User> authenticatedUsers = new ConcurrentHashMap<>();

    public ProfileService(ParentUserDao parentUserDao) {
        this.parentUserDao = parentUserDao;
    }

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        List<String> existingUsernames = parentUserDao.getUsernamesByFirstNameAndLastName(firstName, lastName);

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

    @Transactional(readOnly = true)
    public boolean authenticationUser(String username, String password) {
        Optional<User> userOptional = parentUserDao.getUserByUsername(username, Function.identity());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                authenticatedUsers.put(username, user);
                return true;
            }
        }
        return false;
    }

    public boolean isAuthenticated(String username) {
        return authenticatedUsers.containsKey(username);
    }

    public void logout(String username) {
        authenticatedUsers.remove(username);
    }
}
