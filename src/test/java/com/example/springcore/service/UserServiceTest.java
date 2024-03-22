package com.example.springcore.service;

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

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = TestUtil.createUser("testUser", "testPassword");
    }

    @Test
    void testChangeUserStatus() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));

        userService.changeUserStatus("testUser", true);


        assertThat(testUser.getIsActive(), is(true));

        verify(authenticationService, times(1)).isAuthenticated("testUser");
        verify(userRepository, times(1)).getUserByUserName("testUser");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testChangeUserPassword() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));

        userService.changeUserPassword("testUser", "testPassword", "newPassword");

        assertThat(testUser.getPassword(), is("newPassword"));

        verify(authenticationService, times(1)).isAuthenticated("testUser");
        verify(userRepository, times(1)).getUserByUserName("testUser");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testChangeUserPasswordThrowsEntityNotFoundException() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.changeUserPassword("testUser", "testPassword", "newPassword"));

        verify(authenticationService, times(1)).isAuthenticated("testUser");
        verify(userRepository, times(1)).getUserByUserName("testUser");
    }

    @Test
    void testChangeUserPasswordThrowsIllegalArgumentException() {
        when(userRepository.getUserByUserName("testUser")).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> userService.changeUserPassword("testUser", "wrongPassword", "newPassword"));

        verify(authenticationService, times(1)).isAuthenticated("testUser");
        verify(userRepository, times(1)).getUserByUserName("testUser");
    }
}