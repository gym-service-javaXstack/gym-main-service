package com.example.springcore.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {
    private ProfileService profileService = new ProfileService();
    @Test
    void generateUsername() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "John.Doe";
        String actualUsername = profileService.generateUsername(firstName, lastName);
        assertEquals(expectedUsername, actualUsername, "Generated username should match expected username");

        String expectedUsername2 = "John.Doe1";
        String actualUsername2 = profileService.generateUsername(firstName, lastName);
        assertEquals(expectedUsername2, actualUsername2, "Generated username should match expected username");
    }

    @Test
    void generatePassword() {
        String password = profileService.generatePassword();
        assertNotNull(password, "Generated password should not be null");
        assertEquals(10, password.length(), "Generated password should have length 10");
    }
}