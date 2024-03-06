package com.example.springcore.service;

import com.example.springcore.model.User;
import com.example.springcore.repository.UserDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao<User> userDao;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserService userService;

   /* @Test
    void changeUserStatus() {
        // Given
        User user = TestUtil.createUser("testUser", "password");
        user.setIsActive(false);

        // When
        userService.changeUserStatus(user, true);

        // Then
        assertTrue(user.getIsActive());
        verify(authenticationService).isAuthenticated(user.getUserName());
        verify(userDao).update(user);
    }*/

   /* @Test
    void changeUserPassword_CorrectOldPassword() {
        // Given
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = TestUtil.createUser("testUser", oldPassword);

        // When
        userService.changeUserPassword(user, oldPassword, newPassword);

        // Then
        assertEquals(newPassword, user.getPassword());
        verify(authenticationService).isAuthenticated(user.getUserName());
        verify(userDao).update(user);
    }*/

    /*@Test
    void changeUserPassword_IncorrectOldPassword() {
        // Given
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = TestUtil.createUser("testUser", "incorrectOldPassword");

        // Then
        assertThrows(RuntimeException.class, () -> userService.changeUserPassword(user, oldPassword, newPassword));
    }*/
}