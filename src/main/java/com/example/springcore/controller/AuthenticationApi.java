package com.example.springcore.controller;


import com.example.springcore.dto.NewPasswordRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.util.AuthenticationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public interface AuthenticationApi {

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody UserCredentialsDTO authenticationRequest) throws BadCredentialsException;


    @PutMapping("/login")
    ResponseEntity<Void> changeLoginWithNewPassword(@Valid @RequestBody NewPasswordRequestDTO newPasswordRequestDTORequest);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@NotNull @RequestHeader("Authorization") String authHeader);
}
