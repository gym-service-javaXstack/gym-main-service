package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @Transactional
    public void changeUserStatus(String userName, boolean isActive) {
        log.info("Enter UserService changeUserStatus userName: {}, status: {}", userName, isActive);

        authenticationService.isAuthenticated(userName);

        Optional<User> userByUsername = userRepository.getUserByUserName(userName);
        userByUsername.ifPresent(user -> {
                    user.setIsActive(isActive);
                    userRepository.save(user);
                }
        );
        log.info("Exit UserService changeUserStatus userName: {}, status: {}", userName, isActive);
    }

    @Transactional
    public void changeUserPassword(String userName, String oldPassword, String newPassword) {
        log.info("Enter UserService changeUserPassword userName");

        authenticationService.isAuthenticated(userName);
        User user = userRepository.getUserByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userName));

        if (!Objects.equals(user.getPassword(), oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(newPassword);

        userRepository.save(user);

        log.info("Exit UserService changeUserPassword userName: {}, oldPassword: {}, newPassword: {}",
                userName,
                oldPassword,
                newPassword
        );
    }
}