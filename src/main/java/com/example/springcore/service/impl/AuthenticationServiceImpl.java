package com.example.springcore.service.impl;

import com.example.springcore.dto.AuthenticationResponseDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.exceptions.BruteForceProtectorException;
import com.example.springcore.service.AuthenticationService;
import com.example.springcore.util.BruteForceProtectorService;
import com.example.springcore.util.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BruteForceProtectorService bruteForceProtector;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthenticationResponseDTO login(UserCredentialsDTO request, HttpServletRequest httpRequest) {
        log.info("Enter AuthenticationServiceImpl login method: {}", request);
        String ipAddress = getClientIpAddress(httpRequest);

        if (bruteForceProtector.isBlocked(ipAddress)) {
            throw new BruteForceProtectorException();
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException e) {
            bruteForceProtector.registerFailedAttempt(ipAddress);
            throw e;
        }

        var user = userDetailsService.loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        log.info("Exit AuthenticationServiceImpl login method: {}", jwt);

        bruteForceProtector.resetAttempts(ipAddress);
        return new AuthenticationResponseDTO(jwt);
    }

    @Override
    @Transactional
    public void logout(String authHeader) {
        log.info("Enter AuthenticationServiceImpl logout method: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            jwtService.delete(username);
            log.info("Exit AuthenticationServiceImpl logout method: {}", username);
        } else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
