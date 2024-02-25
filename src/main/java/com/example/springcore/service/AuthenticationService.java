package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.ParentUserDao;
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
    private final ParentUserDao<User> parentUserDao;
    private final Map<String, User> authenticatedUsers = new ConcurrentHashMap<>();

    public AuthenticationService(ParentUserDao<User> parentUserDao) {
        this.parentUserDao = parentUserDao;
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

    @Transactional(readOnly = true)
    public List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName) {
        return parentUserDao.getUsernamesByFirstNameAndLastName(firstName, lastName);
    }
}
