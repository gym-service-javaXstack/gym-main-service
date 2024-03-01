package com.example.springcore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void generateUsername_WhenUsernameNotExists() {
        // Given
        String firstName = "testFirstName";
        String lastName = "testLastName";
        when(authenticationService.getUsernameByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

        // When
        String username = profileService.generateUsername(firstName, lastName);

        // Then
        assertEquals("testFirstName.testLastName", username);
    }

    @Test
    void generateUsername_WhenUsernameExists() {
        // Given
        String firstName = "testFirstName";
        String lastName = "testLastName";
        when(authenticationService.getUsernameByFirstNameAndLastName(firstName, lastName)).thenReturn(Arrays.asList("testFirstName.testLastName"));

        // When
        String username = profileService.generateUsername(firstName, lastName);

        // Then
        assertEquals("testFirstName.testLastName1", username);
    }

    @Test
    void generatePassword() {
        // When
        String password = profileService.generatePassword();

        // Then
        assertNotNull(password);
        assertEquals(10, password.length());
    }
}