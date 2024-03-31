package com.example.springcore.config;


import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUserName(username)
                .map(user -> User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}