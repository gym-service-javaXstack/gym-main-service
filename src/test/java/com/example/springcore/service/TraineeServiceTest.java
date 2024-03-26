package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.mapper.TrainerMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeRepository;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerService trainerService;

    @Mock
    private ProfileService profileService;


    @Mock
    private TrainerMapper trainerMapper;

    @Mock
    private TraineeWithTrainersMapper traineeWithTrainersMapper;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    void testCreateTrainee() {
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();

        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");

        User user = TestUtil.createUser("username", "password");
        Trainee traineeToReturn = TestUtil.createTrainee(user, null);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(traineeToReturn);

        UserCredentialsDTO traineeCredentials = traineeService.createTrainee(traineeDTO);

        assertNotNull(traineeCredentials);
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

        TraineeWithTrainersDTO traineeWithTrainersDTO = new TraineeWithTrainersDTO();
        when(traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(any(Trainee.class))).thenReturn(traineeWithTrainersDTO);

        TraineeWithTrainersDTO result = traineeService.updateTrainee(traineeDTO);

        assertNotNull(result);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        String username = "username";

        User user = TestUtil.createUser(username, "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        traineeService.deleteTrainee(username);

        verify(traineeRepository, times(1)).delete(any(Trainee.class));
    }

    @Test
    void testGetTraineeDTOByUsername() {
        String username = "username";

        User user = TestUtil.createUser(username, "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        TraineeWithTrainersDTO traineeWithTrainersDTO = new TraineeWithTrainersDTO();
        when(traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(any(Trainee.class))).thenReturn(traineeWithTrainersDTO);

        TraineeWithTrainersDTO result = traineeService.getTraineeDTOByUsername(username);

        assertNotNull(result);
    }

    @Test
    void testGetTraineeByUsername() {
        String username = "username";

        User user = TestUtil.createUser(username, "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getTraineeByUsername(username);

        assertNotNull(result);
        assertEquals(trainee, result);
    }
}