package com.example.springcore.controller.impl;

import com.example.springcore.controller.TrainerApi;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TrainerTrainingMapper;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import com.example.springcore.service.UserService;
import io.micrometer.core.annotation.Timed;
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

    private final TrainerTrainingMapper trainerTrainingMapper;
    private final TrainerWithTraineesMapper trainerWithTraineesMapper;

    @Timed(value = "create.trainer.time", description = "Time taken to create trainer")
    @Override
    public ResponseEntity<UserCredentialsDTO> createTrainer(TrainerDTO trainerDTO) {
        return new ResponseEntity<>(trainerService.createTrainer(trainerDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> getTrainerByUserName(String username) {
        TrainerWithTraineesDTO trainerByUsername = trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(trainerService.getTrainerByUserName(username));
        return new ResponseEntity<>(trainerByUsername, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> updateTrainer(TrainerDTO trainerDTO) {
        TrainerWithTraineesDTO trainerWithTraineesDTO = trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(trainerService.updateTrainer(trainerDTO));
        return new ResponseEntity<>(trainerWithTraineesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUsername) {
        List<TrainingDTO> trainerTrainingsByCriteria = trainerTrainingMapper.
                fromTrainingListToTrainerTrainingListDTO(
                        trainingService.getTrainerTrainingsByCriteria(
                                username,
                                fromDate,
                                toDate,
                                traineeUsername
                        )
                );
        return new ResponseEntity<>(trainerTrainingsByCriteria, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeTrainerStatus(String username, boolean isActive) {
        userService.changeUserStatus(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getTrainerSummaryByUsername(String username, int year, int monthValue, String authHeader) {
        Integer trainerSummaryByUsername = trainerService.getTrainerSummaryByUsername(username, year, monthValue, authHeader);
        return new ResponseEntity<>(trainerSummaryByUsername, HttpStatus.OK);
    }
}
