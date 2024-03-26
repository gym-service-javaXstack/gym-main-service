package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainerWithTraineesMapper trainerWithTraineesMapper;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void testCreateTrainer() {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeName("test");

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstName("John");
        trainerDTO.setLastName("Doe");
        trainerDTO.setSpecialization(trainingTypeDTO);

        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");
        when(trainingTypeService.findTrainingTypeByName(anyString())).thenReturn(new TrainingType());

        Trainer trainerToReturn = new Trainer();
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainerToReturn);

        UserCredentialsDTO trainerCredenitals = trainerService.createTrainer(trainerDTO);

        assertNotNull(trainerCredenitals);
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
        when(trainingTypeService.findTrainingTypeByName(anyString())).thenReturn(new TrainingType());
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerWithTraineesDTO trainerWithTraineesDTO = new TrainerWithTraineesDTO();
        when(trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(any(Trainer.class))).thenReturn(trainerWithTraineesDTO);

        TrainerWithTraineesDTO result = trainerService.updateTrainer(trainerDTO);

        assertNotNull(result);
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    void testGetTrainerDTOByUserName() {
        String userName = "username";

        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerRepository.getTrainerByUser_UserName(anyString())).thenReturn(Optional.of(trainer));

        TrainerWithTraineesDTO trainerWithTraineesDTO = new TrainerWithTraineesDTO();
        when(trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(any(Trainer.class))).thenReturn(trainerWithTraineesDTO);

        TrainerWithTraineesDTO result = trainerService.getTrainerDTOByUserName(userName);

        assertNotNull(result);
    }

    @Test
    void testGetTrainerByUserName() {
        String userName = "username";

        Trainer trainer = new Trainer();
        trainer.setUser(new User());

        when(trainerRepository.getTrainerByUser_UserName(anyString())).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getTrainerByUserName(userName);

        assertNotNull(result);
    }

    @Test
    void testGetTrainersByUsernameList() {
        List<String> usernames = Arrays.asList("username1", "username2");

        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        trainers.get(0).setUser(new User());
        trainers.get(1).setUser(new User());

        when(trainerRepository.getTrainersByUser_UserNameIn(anyList())).thenReturn(trainers);

        List<Trainer> result = trainerService.getTrainersByUsernameList(usernames);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}