package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private ProfileService profileService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private TraineeService traineeService;

   /* @Test
    void createTrainee() {
        // Given
        User user = TestUtil.createUser("firstName", "password");
        Trainee trainee = TestUtil.createTrainee(user, null);
        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");
        when(traineeDao.save(any(Trainee.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Trainee result = traineeService.createTrainee(trainee);

        // Then
        assertNotNull(result);
        assertEquals("username", result.getUser().getUserName());
        assertEquals("password", result.getUser().getPassword());
        assertEquals("firstName", result.getUser().getFirstName());
        assertEquals("lastName", result.getUser().getLastName());
        assertTrue(result.getUser().getIsActive());
    }*/

 /*   @Test
    void updateTrainee() {
        // Given
        User user = TestUtil.createUser("username", "password");
        Trainee trainee = TestUtil.createTrainee(user, null);
        when(traineeDao.update(any(Trainee.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Trainee result = traineeService.updateTrainee(trainee);

        // Then
        assertNotNull(result);
        verify(authenticationService).isAuthenticated("username");
    }*/

    @Test
    void deleteTrainee() {
        // Given
        String username = "username";
        Trainee trainee = new Trainee();
        when(traineeDao.getTraineeByUsername(username)).thenReturn(Optional.of(trainee));

        // When
        traineeService.deleteTrainee(username);

        // Then
        verify(traineeDao).delete(trainee);
        verify(authenticationService).isAuthenticated(username);
    }

/*    @Test
    void getTraineeByUsername() {
        // Given
        String username = "username";
        Trainee trainee = new Trainee();
        when(traineeDao.getTraineeByUsername(username)).thenReturn(Optional.of(trainee));

        // When
        Optional<Trainee> result = traineeService.getTraineeByUsername(username);

        // Then
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
        verify(authenticationService).isAuthenticated(username);
    }*/

    @Test
    void updateTraineesTrainersList() {
        // Given
        User user = TestUtil.createUser("traineeUser", "password");
        Trainee trainee = TestUtil.createTrainee(user, null);
        TrainingType trainingType = TestUtil.createTrainingType("specialization");
        Trainer trainer = TestUtil.createTrainer(user, trainingType);

        // When
        traineeService.updateTraineesTrainersList(trainee, trainer);

        // Then
        verify(traineeDao).updateTraineesTrainersList(trainee, trainer);
    }

   /* @Test
    void getTrainersNotAssignedToTrainee() {
        // Given
        String username = "username";
        List<Trainer> trainers = new ArrayList<>();
        when(traineeDao.getTrainersNotAssignedToTrainee(username)).thenReturn(trainers);

        // When
        List<Trainer> result = traineeService.getTrainersNotAssignedToTrainee(username);

        // Then
        assertEquals(trainers, result);
        verify(authenticationService).isAuthenticated(username);
    }*/

    @Test
    void getTraineeTrainingsByCriteria() {
        // Given
        String username = "username";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        String trainerName = "trainerName";
        TrainingType trainingType = TestUtil.createTrainingType("specialization");
        List<Training> trainings = new ArrayList<>();
        when(traineeDao.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType)).thenReturn(trainings);

        // When
        List<Training> result = traineeService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);

        // Then
        assertEquals(trainings, result);
        verify(authenticationService).isAuthenticated(username);
    }
}