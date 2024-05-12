package com.example.springcore.controller;


import com.example.springcore.dto.NewPasswordRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.dto.AuthenticationResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public interface AuthenticationApi {

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponseDTO> login(@RequestBody UserCredentialsDTO authenticationRequest, HttpServletRequest httpRequest) throws BadCredentialsException;


    @PutMapping("/login")
    ResponseEntity<Void> changeLoginWithNewPassword(@Valid @RequestBody NewPasswordRequestDTO newPasswordRequestDTORequest);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@NotNull @RequestHeader("Authorization") String authHeader);

    @GetMapping("/token")
    ResponseEntity<Void> validateToken();
}
