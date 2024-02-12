package com.example.springcore.service;

import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {
    @Mock
    private TrainingDao trainingDao;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    void createTraining() {
        // Given
        Training training = TestUtil.createTestTraining();
        when(trainingDao.createTraining(any(Training.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Training result = trainingService.createTraining(training);

        // Then
        assertThat(result, samePropertyValuesAs(training));
        verify(trainingDao, times(1)).createTraining(any(Training.class));
    }

    @Test
    void getTraining() {
        // Given
        Training expectedTraining = TestUtil.createTestTraining();
        when(trainingDao.getTraining(anyInt())).thenReturn(expectedTraining);

        // When
        Training result = trainingService.getTraining(1);

        // Then
        assertThat(result, samePropertyValuesAs(expectedTraining));
    }

    @Test
    void getAllTrainings() {
        // Given
        List<Training> expectedTrainings = Arrays.asList(TestUtil.createTestTraining(), TestUtil.createTestTraining());
        when(trainingDao.getAll()).thenReturn(expectedTrainings);

        // When
        List<Training> result = trainingService.getAllTrainings();

        // Then
        assertEquals(expectedTrainings.size(), result.size());
        for (int i = 0; i < expectedTrainings.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainings.get(i)));
        }
    }
}