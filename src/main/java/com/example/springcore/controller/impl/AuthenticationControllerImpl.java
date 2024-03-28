package com.example.springcore.controller.impl;

import com.example.springcore.controller.AuthenticationApi;
import com.example.springcore.dto.NewPasswordRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.service.AuthenticationService;
import com.example.springcore.service.UserService;
import com.example.springcore.util.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationApi {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(UserCredentialsDTO authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeLoginWithNewPassword(NewPasswordRequestDTO newPasswordRequestDTORequest) {
        userService.changeUserPassword(
                newPasswordRequestDTORequest.getUsername(),
                newPasswordRequestDTORequest.getPassword(),
                newPasswordRequestDTORequest.getNewPassword()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> logout(String authHeader) {
        authenticationService.logout(authHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
