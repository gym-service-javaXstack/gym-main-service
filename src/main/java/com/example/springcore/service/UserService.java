package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao<User> userDao;
    private final AuthenticationService authenticationService;

    @Transactional
    public void changeUserStatus(String username, boolean isActive) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        userDao.changeUserStatus(username, isActive);
    }

    @Transactional
    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        userDao.changePassword(username, oldPassword, newPassword);
        log.info("Updated trainer password: {}", username);
    }
}