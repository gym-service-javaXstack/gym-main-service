package com.example.springcore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void testGenerateUsername() {
        List<String> existingUsernames = Arrays.asList("John.Doe", "John.Doe1", "John.Doe2");
        when(authenticationService.getUsernameByFirstNameAndLastName("John", "Doe")).thenReturn(existingUsernames);

        String username = profileService.generateUsername("John", "Doe");

        assertThat(username, is("John.Doe3"));
        verify(authenticationService, times(1)).getUsernameByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testGeneratePassword() {
        String password = profileService.generatePassword();

        assertThat(password, notNullValue());
        assertThat(password.length(), is(10));
    }
}