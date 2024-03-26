package com.example.springcore.controller.impl;

import com.example.springcore.controller.AuthenticationApi;
import com.example.springcore.dto.NewPasswordRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.service.UserService;
import com.example.springcore.util.AuthenticationResponse;
import com.example.springcore.util.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationApi {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(UserCredentialsDTO authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
    }
}
