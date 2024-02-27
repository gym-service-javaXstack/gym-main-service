package com.example.springcore.service;

import com.example.springcore.repository.TrainerDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private TrainerDao trainerDao;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private TrainerService trainerService;

}