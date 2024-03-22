package com.example.springcore.service;

import com.example.springcore.exceptions.UserNotAuthenticatedException;
import com.example.springcore.model.User;
import com.example.springcore.repository.UserRepository;
import com.example.springcore.util.TestUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = TestUtil.createUser("testUser", "testPassword");
    }

    @Test
    void testAuthenticationUser() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));

        boolean result = authenticationService.authenticationUser("testUser", "testPassword");

        assertThat(result, is(true));
        verify(userRepository, times(1)).getUserByUserName("testUser");
    }

    @Test
    void testAuthenticationUserThrowsEntityNotFoundException() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authenticationService.authenticationUser("testUser", "testPassword"));

        verify(userRepository, times(1)).getUserByUserName("testUser");
    }

    @Test
    void testAuthenticationUserThrowsIllegalArgumentException() {
        testUser.setPassword("wrongPassword");
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> authenticationService.authenticationUser("testUser", "testPassword"));

        verify(userRepository, times(1)).getUserByUserName("testUser");
    }

    @Test
    void testIsAuthenticated() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));
        authenticationService.authenticationUser("testUser", "testPassword");

        authenticationService.isAuthenticated("testUser");
    }

    @Test
    void testLogout() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));
        authenticationService.authenticationUser("testUser", "testPassword");

        authenticationService.logout("testUser");

        assertThrows(UserNotAuthenticatedException.class, () -> authenticationService.isAuthenticated("testUser"));
    }

    @Test
    void testGetUsernameByFirstNameAndLastName() {
        List<String> expectedUserNames = Arrays.asList("john.doe", "john.doe1", "john.doe2");
        when(userRepository.getUserNamesByFirstNameAndLastName("John.Doe")).thenReturn(expectedUserNames);

        List<String> actualUserNames = authenticationService.getUsernameByFirstNameAndLastName("John", "Doe");

        assertThat(actualUserNames, is(expectedUserNames));
        verify(userRepository, times(1)).getUserNamesByFirstNameAndLastName("John.Doe");
    }
}