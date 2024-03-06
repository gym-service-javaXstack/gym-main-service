package com.example.springcore.controller.impl;

import com.example.springcore.controller.AuthenticationApi;
import com.example.springcore.dto.NewPasswordDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.service.AuthenticationService;
import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationApi {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Override
    public ResponseEntity<Void> loginUserWithCredentials(UserCredentialsDTO userCredentialsDTORequest) {
        boolean isAuthenticated = authenticationService.authenticationUser(
                userCredentialsDTORequest.getUsername(),
                userCredentialsDTORequest.getPassword()
        );

        if (isAuthenticated) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Void> changeLoginWithNewPassword(NewPasswordDTO newPasswordDTORequest) {
        userService.changeUserPassword(
                newPasswordDTORequest.getUsername(),
                newPasswordDTORequest.getPassword(),
                newPasswordDTORequest.getNewPassword()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
