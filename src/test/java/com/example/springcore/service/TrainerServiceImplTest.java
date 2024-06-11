package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerRepository;
import com.example.springcore.service.impl.ProfileServiceImpl;
import com.example.springcore.service.impl.TrainerServiceImpl;
import com.example.springcore.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ProfileServiceImpl profileServiceImpl;

    @Mock
    private TrainingTypeServiceImpl trainingTypeServiceImpl;

    @Mock
    private TrainerWithTraineesMapper trainerWithTraineesMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TrainerServiceImpl trainerServiceImpl;

    @Test
    void testCreateTrainer() {
        // Arrange
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeName("test");

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstName("John");
        trainerDTO.setLastName("Doe");
        trainerDTO.setSpecialization(trainingTypeDTO);

        String expectedUsername = "username";
        String expectedPassword = "password";
        when(profileServiceImpl.generateUsername(anyString(), anyString())).thenReturn(expectedUsername);
        when(profileServiceImpl.generatePassword()).thenReturn(expectedPassword);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        when(trainingTypeServiceImpl.findTrainingTypeByName(anyString())).thenReturn(new TrainingType());

        Trainer trainerToReturn = new Trainer();
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainerToReturn);

        // Act
        UserCredentialsDTO trainerCredentials = trainerServiceImpl.createTrainer(trainerDTO);

        // Assert
        assertNotNull(trainerCredentials);
        assertEquals(expectedUsername, trainerCredentials.getUsername());
        assertEquals(expectedPassword, trainerCredentials.getPassword());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testUpdateTrainer() {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeName("testType");

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUserName("username");
        trainerDTO.setSpecialization(trainingTypeDTO);

        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerRepository.getTrainerByUser_UserName(anyString())).thenReturn(Optional.of(trainer));
        when(trainingTypeServiceImpl.findTrainingTypeByName(anyString())).thenReturn(new TrainingType());
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);


        Trainer result = trainerServiceImpl.updateTrainer(trainerDTO);

        assertNotNull(result);
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    void testGetTrainerByUserName() {
        String userName = "username";

        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerRepository.getTrainerByUser_UserName(anyString())).thenReturn(Optional.of(trainer));

        Trainer result = trainerServiceImpl.getTrainerByUserName(userName);

        assertNotNull(result);
    }

    @Test
    void testGetTrainersByUsernameList() {
        List<String> usernames = Arrays.asList("username1", "username2");

        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        trainers.get(0).setUser(new User());
        trainers.get(1).setUser(new User());

        when(trainerRepository.getTrainersByUser_UserNameIn(anyList())).thenReturn(trainers);

        List<Trainer> result = trainerServiceImpl.getTrainersByUsernameList(usernames);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}