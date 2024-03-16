package com.example.springcore.controller;


import com.example.springcore.dto.NewPasswordRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/login")
public interface AuthenticationApi {

    @GetMapping
    ResponseEntity<Void> loginUserWithCredentials(@RequestParam String username, @RequestParam String password);

    @PutMapping
    ResponseEntity<Void> changeLoginWithNewPassword(@Valid @RequestBody NewPasswordRequestDTO newPasswordRequestDTORequest);
}
