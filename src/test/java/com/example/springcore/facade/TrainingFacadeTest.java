package com.example.springcore.facade;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingFacadeTest {
    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingFacade trainingFacade;

    @Test
    void createTrainer() {
        // Given
        Trainer trainer = TestUtil.createTestTrainer();
        when(trainerService.createTrainer(any(Trainer.class))).thenReturn(trainer);

        // When
        Trainer result = trainingFacade.createTrainer(trainer);

        // Then
        assertThat(result, samePropertyValuesAs(trainer));
    }

    @Test
    void createTrainee() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        when(traineeService.createTrainee(any(Trainee.class))).thenReturn(trainee);

        // When
        Trainee result = trainingFacade.createTrainee(trainee);

        // Then
        assertThat(result, samePropertyValuesAs(trainee));
    }

    @Test
    void deleteTrainee() {
        // Given
        Integer id = 1;

        // When
        trainingFacade.deleteTrainee(id);

        // Then
        verify(traineeService, times(1)).deleteTrainee(id);
    }

    @Test
    void createTrainingScenario() {
        // Given
        Integer trainerId = 1;
        Integer traineeId = 1;
        Training training = TestUtil.createTestTraining();
        when(trainerService.getTrainer(trainerId)).thenReturn(TestUtil.createTestTrainer());
        when(traineeService.getTrainee(traineeId)).thenReturn(TestUtil.createTestTrainee());
        when(trainingService.createTraining(any(Training.class))).thenReturn(training);

        // When
        trainingFacade.createTrainingScenario(trainerId, traineeId, training);

        // Then
        verify(trainerService, times(1)).getTrainer(trainerId);
        verify(traineeService, times(1)).getTrainee(traineeId);
        verify(trainingService, times(1)).createTraining(any(Training.class));
    }

    @Test
    void createTrainingScenario_TrainerNotFound() {
        // Given
        Integer trainerId = 1;
        Integer traineeId = 1;
        Training training = TestUtil.createTestTraining();
        when(trainerService.getTrainer(trainerId)).thenReturn(null);

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingFacade.createTrainingScenario(trainerId, traineeId, training);
        });

        // Then
        assertEquals("Trainer not found", exception.getMessage());
    }

    @Test
    void createTrainingScenario_TraineeNotFound() {
        // Given
        Integer trainerId = 1;
        Integer traineeId = 1;
        Training training = TestUtil.createTestTraining();
        when(trainerService.getTrainer(trainerId)).thenReturn(TestUtil.createTestTrainer());
        when(traineeService.getTrainee(traineeId)).thenReturn(null);

        // When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingFacade.createTrainingScenario(trainerId, traineeId, training);
        });

        // Then
        assertEquals("Trainee not found", exception.getMessage());
    }

    @Test
    void getAllTrainings() {
        // Given
        List<Training> expectedTrainings = Arrays.asList(TestUtil.createTestTraining(), TestUtil.createTestTraining());
        when(trainingService.getAllTrainings()).thenReturn(expectedTrainings);

        // When
        List<Training> result = trainingFacade.getAllTrainings();

        // Then
        assertEquals(expectedTrainings.size(), result.size());
        for (int i = 0; i < expectedTrainings.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainings.get(i)));
        }
    }

    @Test
    void getAllTrainee() {
        // Given
        List<Trainee> expectedTrainees = Arrays.asList(TestUtil.createTestTrainee(), TestUtil.createTestTrainee());
        when(traineeService.getAllTrainees()).thenReturn(expectedTrainees);

        // When
        List<Trainee> result = trainingFacade.getAllTrainee();

        // Then
        assertEquals(expectedTrainees.size(), result.size());
        for (int i = 0; i < expectedTrainees.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainees.get(i)));
        }
    }

    @Test
    void getAllTrainers() {
        // Given
        List<Trainer> expectedTrainers = Arrays.asList(TestUtil.createTestTrainer(), TestUtil.createTestTrainer());
        when(trainerService.getAllTrainers()).thenReturn(expectedTrainers);

        // When
        List<Trainer> result = trainingFacade.getAllTrainers();

        // Then
        assertEquals(expectedTrainers.size(), result.size());
        for (int i = 0; i < expectedTrainers.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainers.get(i)));
        }
    }
}