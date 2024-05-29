package com.example.springcore.service;

import java.util.List;

public interface UserService {
    void changeUserStatus(String userName, boolean isActive);

    void changeUserPassword(String userName, String oldPassword, String newPassword);

    List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName);
}
