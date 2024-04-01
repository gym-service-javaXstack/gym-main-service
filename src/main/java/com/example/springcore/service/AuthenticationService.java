package com.example.springcore.service;

import com.example.springcore.dto.AuthenticationResponseDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    AuthenticationResponseDTO login(UserCredentialsDTO request, HttpServletRequest httpRequest);

    void logout(String authHeader);
}