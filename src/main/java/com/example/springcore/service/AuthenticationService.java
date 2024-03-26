package com.example.springcore.service;

import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.util.AuthenticationResponse;
import com.example.springcore.util.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse logIn(UserCredentialsDTO request) {
        log.info("Enter AuthenticationService logIn method: {}", request);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        log.info("Exit AuthenticationService logIn method: {}", jwt);
        return new AuthenticationResponse(jwt);
    }
}
