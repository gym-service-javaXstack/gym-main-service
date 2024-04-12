package com.example.springcore.service;

import com.example.springcore.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void changeUserStatus(String userName, boolean isActive);

    void changeUserPassword(String userName, String oldPassword, String newPassword);

    List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> getUserByUserName(String username);
}
