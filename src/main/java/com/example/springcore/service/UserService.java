package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao<User> userDao;
    private final AuthenticationService authenticationService;

    @Transactional
    public void changeUserStatus(User user, boolean isActive) {
        authenticationService.isAuthenticated(user.getUserName());
        user.setIsActive(isActive);
        userDao.update(user);
    }

    @Transactional
    public User changeUserPassword(User user, String oldPassword, String newPassword) {
        authenticationService.isAuthenticated(user.getUserName());
        if (!Objects.equals(user.getPassword(), oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setPassword(newPassword);

        userDao.update(user);

        log.info("Updated trainer password: {}", user.getUserName());
        return user;
    }
}