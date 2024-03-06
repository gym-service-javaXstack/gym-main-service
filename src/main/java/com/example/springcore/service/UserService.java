package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao<User> userDao;
    private final AuthenticationService authenticationService;

    @Transactional
    public void changeUserStatus(String username, boolean isActive) {
        authenticationService.isAuthenticated(username);
        Optional<User> userByUsername = userDao.getUserByUsername(username);
        userByUsername.ifPresent(user -> {
                    user.setIsActive(isActive);
                    userDao.update(user);
                }
        );
    }

    @Transactional
    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        authenticationService.isAuthenticated(username);
        User user = userDao.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (!Objects.equals(user.getPassword(), oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(newPassword);

        userDao.update(user);

        log.info("Updated trainer password: {}", user.getUserName());
    }
}