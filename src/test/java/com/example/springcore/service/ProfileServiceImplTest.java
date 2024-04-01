package com.example.springcore.service;

import com.example.springcore.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileServiceImpl;


    @Test
    void testGeneratePassword() {
        String password = profileServiceImpl.generatePassword();

        assertThat(password, notNullValue());
        assertThat(password.length(), is(10));
    }
}