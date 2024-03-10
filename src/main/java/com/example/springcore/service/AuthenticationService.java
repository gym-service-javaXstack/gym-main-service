package com.example.springcore.service;

import com.example.springcore.exceptions.UserNotAuthenticatedException;
import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AuthenticationService {
    private final UserDao<User> userDao;
    private final Map<String, User> authenticatedUsers = new ConcurrentHashMap<>();

    public AuthenticationService(UserDao<User> userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public boolean authenticationUser(String username, String password) {
        User user = userDao.getUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password for user: " + username);
        }
        authenticatedUsers.put(username, user);
        log.info("AuthenticationService authenticationUser username: {}", username);
        return true;
    }

    public void isAuthenticated(String username) {
        if (!authenticatedUsers.containsKey(username)) {
            throw new UserNotAuthenticatedException("User not authenticated: " + username);
        }
    }

    public void logout(String username) {
        authenticatedUsers.remove(username);
    }

    @Transactional(readOnly = true)
    public List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName) {
        return userDao.getUsernamesByFirstNameAndLastName(firstName, lastName);
    }
}
