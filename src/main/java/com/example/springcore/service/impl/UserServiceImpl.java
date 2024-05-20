package com.example.springcore.service.impl;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserRepository;
import com.example.springcore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changeUserStatus(String username, boolean isActive) {
        log.info("Enter UserServiceImpl changeUserStatus username: {}, status: {}", username, isActive);

        Optional<User> userByUsername = userRepository.getUserByUserName(username);
        userByUsername.ifPresent(user -> {
                    user.setIsActive(isActive);
                    userRepository.save(user);
                }
        );
        log.info("Exit UserServiceImpl changeUserStatus username: {}, status: {}", username, isActive);
    }

    @Override
    @Transactional
    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        log.info("Enter UserServiceImpl changeUserPassword username");

        User user = userRepository.getUserByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        log.info("Exit UserServiceImpl changeUserPassword username: {}, oldPassword: {}, newPassword: {}",
                username,
                oldPassword,
                newPassword
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        return userRepository.getUserNamesByFirstNameAndLastName(baseUserName);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUserName(String username) {
        return userRepository.getUserByUserName(username);
    }
}