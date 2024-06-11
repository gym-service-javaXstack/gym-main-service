package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainerListToUpdateRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeRepository;
import com.example.springcore.service.impl.ProfileServiceImpl;
import com.example.springcore.service.impl.TraineeServiceImpl;
import com.example.springcore.service.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private ProfileServiceImpl profileServiceImpl;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GymReportsService gymReportsService;

    @Mock
    private TraineeWithTrainersMapper traineeWithTrainersMapper;

    @InjectMocks
    private TraineeServiceImpl traineeServiceImpl;

    @Mock
    private TrainerService trainerService;

    @Test
    void testCreateTrainee() {
        // Arrange
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();

        String expectedUsername = "username";
        String expectedRawPassword = "password";
        String expectedEncodedPassword = "encodedPassword";

        when(profileServiceImpl.generateUsername(anyString(), anyString())).thenReturn(expectedUsername);
        when(profileServiceImpl.generatePassword()).thenReturn(expectedRawPassword);
        when(passwordEncoder.encode(expectedRawPassword)).thenReturn(expectedEncodedPassword);

        Trainee traineeToReturn = TestUtil.createTrainee(TestUtil.createUser(expectedUsername, expectedEncodedPassword), null);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(traineeToReturn);

        // Act
        UserCredentialsDTO traineeCredentials = traineeServiceImpl.createTrainee(traineeDTO);

        // Assert
        assertNotNull(traineeCredentials);
        assertEquals(expectedUsername, traineeCredentials.getUsername());
        assertEquals(expectedRawPassword, traineeCredentials.getPassword());
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testUpdateTrainee() {
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();
        traineeDTO.setUserName("username");

        User user = TestUtil.createUser("username", "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        Trainee result = traineeServiceImpl.updateTrainee(traineeDTO);

        assertNotNull(result);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        String traineeUsername = "traineeUsername";
        String trainerUsername = "trainerUsername";

        TrainingType trainingType = TestUtil.createTrainingType("test");

        User traineeUser = TestUtil.createUser(traineeUsername, "password");
        Trainee trainee = TestUtil.createTrainee(traineeUser, null);

        User trainerUser = TestUtil.createUser(trainerUsername, "password");
        Trainer trainer = TestUtil.createTrainer(trainerUser, trainingType);

        Training training = TestUtil.createTraining(trainee, trainer, trainingType);

        List<Training> trainings = Collections.singletonList(training);

        trainee.setTrainings(trainings);
        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        traineeServiceImpl.deleteTrainee(traineeUsername);

        verify(traineeRepository, times(1)).delete(any(Trainee.class));
    }

    @Test
    void testGetTraineeByUsername() {
        String username = "username";

        User user = TestUtil.createUser(username, "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        Trainee result = traineeServiceImpl.getTraineeByUsername(username);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void testUpdateTrainersListInTraineeByUsername() {
        String traineeUsername = "traineeUsername";
        String trainerUsername = "trainerUsername";

        User traineeUser = TestUtil.createUser(traineeUsername, "password");
        Trainee trainee = TestUtil.createTrainee(traineeUser, null);

        User trainerUser = TestUtil.createUser(trainerUsername, "password");
        Trainer trainer = TestUtil.createTrainer(trainerUser, null);

        TraineeWithTrainerListToUpdateRequestDTO request = new TraineeWithTrainerListToUpdateRequestDTO();
        request.setUserName(traineeUsername);

        TraineeWithTrainerListToUpdateRequestDTO.TrainerUsername trainerUsernameDTO = new TraineeWithTrainerListToUpdateRequestDTO.TrainerUsername(trainerUsername);
        request.setTrainers(Collections.singletonList(trainerUsernameDTO));

        when(traineeRepository.getTraineeByUser_UserName(eq(traineeUsername))).thenReturn(Optional.of(trainee));
        when(trainerService.getTrainersByUsernameList(anyList())).thenReturn(Collections.singletonList(trainer));

        traineeServiceImpl.updateTrainersListInTraineeByUsername(request);

        verify(traineeRepository, times(1)).getTraineeByUser_UserName(traineeUsername);
        verify(trainerService, times(1)).getTrainersByUsernameList(anyList());
        verify(traineeRepository, times(1)).save(any(Trainee.class));

        assertEquals(1, trainee.getTrainers().size());
        Assertions.assertTrue(trainee.getTrainers().contains(trainer));
    }
}