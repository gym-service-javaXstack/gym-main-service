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
        authenticationService.isAuthenticated(username);
        userDao.changeUserStatus(username, isActive);
    }

    @Transactional
    public User changeUserPassword(String username, String oldPassword, String newPassword) {
        authenticationService.isAuthenticated(username);
        User user = userDao.changePassword(username, oldPassword, newPassword);
        log.info("Updated trainer password: {}", username);
        return user;
    }
}