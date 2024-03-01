package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UserDao<User> userDao;

    @InjectMocks
    private AuthenticationService authenticationService;

    private User createTestUser(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        return user;
    }

    @Test
    void testAuthenticationUser() {
        // Given
        String username = "testUser";
        String password = "testPassword";
        User user = createTestUser(username, password);
        when(userDao.getUserByUsername(username)).thenReturn(Optional.of(user));

        // When
        boolean result = authenticationService.authenticationUser(username, password);

        // Then
        assertTrue(result);
        verify(userDao).getUserByUsername(username);
    }

    @Test
    void isAuthenticated() {
        // Given
        String username = "testUser";
        String password = "password";
        User user = createTestUser(username, password);
        when(userDao.getUserByUsername(username)).thenReturn(Optional.of(user));

        // When
        authenticationService.authenticationUser(username, password);

        // Then
        assertDoesNotThrow(() -> authenticationService.isAuthenticated(username));
    }

    @Test
    void testAuthenticationUser_UserDoesNotExist() {
        // Given
        String username = "nonExistingUser";
        String password = "password";
        when(userDao.getUserByUsername(username)).thenReturn(Optional.empty());

        // When
        boolean result = authenticationService.authenticationUser(username, password);

        // Then
        assertFalse(result);
        verify(userDao).getUserByUsername(username);
    }

    @Test
    void testAuthenticationUser_WrongPassword() {
        // Given
        String username = "testUser";
        String password = "wrongPassword";
        User user = createTestUser(username, "correctPassword");
        when(userDao.getUserByUsername(username)).thenReturn(Optional.of(user));

        // When
        boolean result = authenticationService.authenticationUser(username, password);

        // Then
        assertFalse(result);
        verify(userDao).getUserByUsername(username);
    }

    @Test
    void logout() {
        // Given
        String username = "testUser";
        String password = "password";
        User user = createTestUser(username, password);
        when(userDao.getUserByUsername(username)).thenReturn(Optional.of(user));
        authenticationService.authenticationUser(username, password);

        // When
        authenticationService.logout(username);

        // Then
        assertThrows(RuntimeException.class, () -> authenticationService.isAuthenticated(username));
    }

    @Test
    void getUsernameByFirstNameAndLastName() {
        // Given
        String firstName = "testFirstName";
        String lastName = "testLastName";
        List<String> usernames = Arrays.asList("username1", "username2");
        when(userDao.getUsernamesByFirstNameAndLastName(firstName, lastName)).thenReturn(usernames);

        // When
        List<String> result = authenticationService.getUsernameByFirstNameAndLastName(firstName, lastName);

        // Then
        assertEquals(usernames, result);
        verify(userDao).getUsernamesByFirstNameAndLastName(firstName, lastName);
    }
}