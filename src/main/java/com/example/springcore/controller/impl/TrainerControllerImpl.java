package com.example.springcore.controller.impl;

import com.example.springcore.controller.TrainerApi;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainer;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TrainerControllerImpl implements TrainerApi {
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;

    @Override
    public ResponseEntity<UserCredentialsDTO> createTrainer(TrainerDTO trainerDTO) {
        Trainer trainer = trainerService.createTrainer(trainerDTO);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername(trainer.getUser().getUserName());
        userCredentialsDTO.setPassword(trainer.getUser().getPassword());
        return new ResponseEntity<>(userCredentialsDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> getTrainerByUserName(String username) {
        TrainerWithTraineesDTO trainerByUsername = trainerService.getTrainerDTOByUserName(username);
        return new ResponseEntity<>(trainerByUsername, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> updateTrainer(TrainerDTO trainerDTO) {
        TrainerWithTraineesDTO trainerWithTraineesDTO = trainerService.updateTrainer(trainerDTO);
        return new ResponseEntity<>(trainerWithTraineesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUsername) {
        List<TrainingDTO> trainerTrainingsByCriteria = trainingService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeUsername);
        return new ResponseEntity<>(trainerTrainingsByCriteria, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeTrainerStatus(String username, boolean isActive) {
        userService.changeUserStatus(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
