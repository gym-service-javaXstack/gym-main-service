package com.example.springcore.service;

import com.example.springcore.exceptions.UserNotAuthenticatedException;
import com.example.springcore.model.User;
import com.example.springcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final Map<String, User> authenticatedUsers = new ConcurrentHashMap<>();


    @Transactional(readOnly = true)
    public boolean authenticationUser(String userName, String password) {
        log.info("Enter AuthenticationService authenticationUser userName: {}", userName);

        User user = userRepository.getUserByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userName));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password for user: " + userName);
        }

        authenticatedUsers.put(userName, user);

        log.info("Exit AuthenticationService authenticationUser userName: {}", userName);
        return true;
    }

    public void isAuthenticated(String userName) {
        if (!authenticatedUsers.containsKey(userName)) {
            throw new UserNotAuthenticatedException("User not authenticated: " + userName);
        }
    }

    public void logout(String userName) {
        authenticatedUsers.remove(userName);
    }

    @Transactional(readOnly = true)
    public List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        return userRepository.getUserNamesByFirstNameAndLastName(baseUserName);
    }
}
