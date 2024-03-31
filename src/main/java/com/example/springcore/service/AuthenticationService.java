package com.example.springcore.service;

import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.exceptions.BruteForceProtectorException;
import com.example.springcore.dto.AuthenticationResponseDTO;
import com.example.springcore.util.BruteForceProtectorService;
import com.example.springcore.util.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BruteForceProtectorService bruteForceProtector;

    @Transactional
    public AuthenticationResponseDTO login(UserCredentialsDTO request, HttpServletRequest httpRequest) {
        log.info("Enter AuthenticationService login method: {}", request);
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

        var user = userService.loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        log.info("Exit AuthenticationService login method: {}", jwt);

        bruteForceProtector.resetAttempts(ipAddress);
        return new AuthenticationResponseDTO(jwt);
    }

    @Transactional
    public void logout(String authHeader) {
        log.info("Enter AuthenticationService logout method: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            jwtService.delete(username);
            log.info("Exit AuthenticationService logout method: {}", username);
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
