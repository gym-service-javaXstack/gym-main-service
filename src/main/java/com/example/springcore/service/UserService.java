package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserRepository;
import com.example.springcore.util.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public void changeUserStatus(String userName, boolean isActive) {
        log.info("Enter UserService changeUserStatus userName: {}, status: {}", userName, isActive);

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

    @Transactional(readOnly = true)
    public List<String> getUsernameByFirstNameAndLastName(String firstName, String lastName) {
        String baseUserName = firstName + "." + lastName;
        return userRepository.getUserNamesByFirstNameAndLastName(baseUserName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByUserName = userRepository.getUserByUserName(username);
        return userByUserName
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}