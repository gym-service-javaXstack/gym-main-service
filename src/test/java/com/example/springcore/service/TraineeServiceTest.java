package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.impl.TraineeDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeDao traineeDao;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    void createTrainee() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");
        when(traineeDao.save(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trainee result = traineeService.createTrainee(trainee);

        // Then
        assertThat(result, samePropertyValuesAs(trainee));
        verify(traineeDao, times(1)).save(any(Trainee.class));
    }

    @Test
    void updateTrainee() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        when(traineeDao.update(any(Trainee.class), anyInt())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trainee result = traineeService.updateTrainee(trainee);

        // Then
        assertThat(result, samePropertyValuesAs(trainee));
        verify(traineeDao, times(1)).update(any(Trainee.class), anyInt());
    }

    @Test
    void getTrainee() {
        // Given
        Trainee expectedTrainee = TestUtil.createTestTrainee();
        when(traineeDao.get(anyInt())).thenReturn(Optional.of(expectedTrainee));

        // When
        Optional<Trainee> result = traineeService.getTrainee(1);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get(), samePropertyValuesAs(expectedTrainee));
    }

    @Test
    void getAllTrainees() {
        // Given
        List<Trainee> expectedTrainees = Arrays.asList(TestUtil.createTestTrainee(), TestUtil.createTestTrainee());
        when(traineeDao.getAll()).thenReturn(expectedTrainees);

        // When
        List<Trainee> result = traineeService.getAllTrainees();

        // Then
        assertEquals(expectedTrainees.size(), result.size());
        for (int i = 0; i < expectedTrainees.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainees.get(i)));
        }
    }

    @Test
    void deleteTrainee() {
        // Given
        Integer id = 1;

        // When
        traineeService.deleteTrainee(id);

        // Then
        verify(traineeDao, times(1)).delete(id);
    }
}