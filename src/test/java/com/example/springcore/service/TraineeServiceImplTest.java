package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeRepository;
import com.example.springcore.service.impl.ProfileServiceImpl;
import com.example.springcore.service.impl.TraineeServiceImpl;
import com.example.springcore.service.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private TraineeWithTrainersMapper traineeWithTrainersMapper;

    @InjectMocks
    private TraineeServiceImpl traineeServiceImpl;

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

        TraineeWithTrainersDTO traineeWithTrainersDTO = new TraineeWithTrainersDTO();
        when(traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(any(Trainee.class))).thenReturn(traineeWithTrainersDTO);

        TraineeWithTrainersDTO result = traineeServiceImpl.updateTrainee(traineeDTO);

        assertNotNull(result);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void testDeleteTrainee() {
        String username = "username";

        User user = TestUtil.createUser(username, "password");
        Trainee trainee = TestUtil.createTrainee(user, null);

        when(traineeRepository.getTraineeByUser_UserName(anyString())).thenReturn(Optional.of(trainee));

        traineeServiceImpl.deleteTrainee(username);

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

        TraineeWithTrainersDTO result = traineeServiceImpl.getTraineeDTOByUsername(username);

        assertNotNull(result);
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
}