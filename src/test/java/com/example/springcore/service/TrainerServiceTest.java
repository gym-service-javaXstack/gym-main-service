package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerDao;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private ProfileService profileService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private TrainerService trainerService;

    /*@Test
    void createTrainer() {
        // Given
        User user = TestUtil.createUser("firstName", "password");
        TrainingType trainingType = TestUtil.createTrainingType("specialization");
        Trainer trainer = TestUtil.createTrainer(user, trainingType);
        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");
        when(trainerDao.save(any(Trainer.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Trainer result = trainerService.createTrainer(trainer);

        // Then
        assertNotNull(result);
        assertEquals("username", result.getUser().getUserName());
        assertEquals("password", result.getUser().getPassword());
        assertEquals("firstName", result.getUser().getFirstName());
        assertEquals("lastName", result.getUser().getLastName());
        assertEquals("specialization", result.getSpecialization().getTrainingTypeName());
        assertTrue(result.getUser().getIsActive());
    }*/

/*
    @Test
    void updateTrainer() {
        // Given
        User user = TestUtil.createUser("username", "password");
        TrainingType trainingType = TestUtil.createTrainingType("specialization");
        Trainer trainer = TestUtil.createTrainer(user, trainingType);
        when(trainerDao.update(any(Trainer.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Trainer result = trainerService.updateTrainer(trainer);

        // Then
        assertNotNull(result);
        verify(authenticationService).isAuthenticated("username");
    }
*/

/*    @Test
    void getTrainerByUsername() {
        // Given
        String username = "username";
        Trainer trainer = new Trainer();
        when(trainerDao.getTrainerByUsername(username)).thenReturn(Optional.of(trainer));

        // When
        Optional<Trainer> result = trainerService.getTrainerByUsername(username);

        // Then
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        verify(authenticationService).isAuthenticated(username);
    }*/

    @Test
    void getTrainerTrainingsByCriteria() {
        // Given
        String username = "username";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        String traineeName = "traineeName";
        List<Training> trainings = new ArrayList<>();
        when(trainerDao.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName)).thenReturn(trainings);

        // When
        List<Training> result = trainerService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName);

        // Then
        assertEquals(trainings, result);
        verify(authenticationService).isAuthenticated(username);
    }
}