package com.example.springcore.service;

import com.example.springcore.service.impl.ProfileServiceImpl;
import com.example.springcore.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private ProfileServiceImpl profileServiceImpl;

    @Test
    void shouldReturnBaseUsername_WhenNoUsersWithSameFirstNameAndLastName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = firstName + "." + lastName;

        when(userServiceImpl.getUsernameByFirstNameAndLastName(firstName, lastName)).thenReturn(Collections.emptyList());

        // Act
        String generatedUsername = profileServiceImpl.generateUsername(firstName, lastName);

        // Assert
        assertEquals(expectedUsername, generatedUsername);
    }

    @Test
    void shouldReturnBaseUsernameWithNumber_WhenOneUserWithSameFirstNameAndLastNameExists() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = firstName + "." + lastName;
        String expectedUsername = baseUsername + "1";

        when(userServiceImpl.getUsernameByFirstNameAndLastName(firstName, lastName)).thenReturn(Arrays.asList(baseUsername));

        // Act
        String generatedUsername = profileServiceImpl.generateUsername(firstName, lastName);

        // Assert
        assertEquals(expectedUsername, generatedUsername);
    }

    @Test
    void shouldReturnBaseUsernameWithMaxNumber_WhenMultipleUsersWithSameFirstNameAndLastNameExists() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = firstName + "." + lastName;

        when(userServiceImpl.getUsernameByFirstNameAndLastName(firstName, lastName))
                .thenReturn(Arrays.asList(baseUsername, baseUsername + "1", baseUsername + "2"));

        String expectedUsername = baseUsername + "3";

        // Act
        String generatedUsername = profileServiceImpl.generateUsername(firstName, lastName);

        // Assert
        assertEquals(expectedUsername, generatedUsername);
    }

    @Test
    void testGeneratePassword() {
        String password = profileServiceImpl.generatePassword();

        assertThat(password, notNullValue());
        assertThat(password.length(), is(10));
    }
}