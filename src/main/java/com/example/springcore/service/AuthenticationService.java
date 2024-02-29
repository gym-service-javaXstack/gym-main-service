package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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
        Optional<User> userOptional = userDao.getUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                authenticatedUsers.put(username, user);
                return true;
            }
        }
        return false;
    }

    public void isAuthenticated(String username) {
        if (!authenticatedUsers.containsKey(username)){
            throw new RuntimeException("User is not authenticated");
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
