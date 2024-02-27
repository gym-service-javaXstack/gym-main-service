package com.example.springcore.service;

import com.example.springcore.repository.TraineeDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeDao traineeDao;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private TraineeService traineeService;

}