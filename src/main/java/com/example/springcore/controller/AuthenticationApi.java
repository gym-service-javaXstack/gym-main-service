package com.example.springcore.controller;


import com.example.springcore.dto.NewPasswordDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/login")
public interface AuthenticationApi {

    @GetMapping
    ResponseEntity<Void> loginUserWithCredentials(@Valid @RequestBody UserCredentialsDTO userCredentialsDTORequest);

    @PutMapping
    ResponseEntity<Void> changeLoginWithNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTORequest);
}
