package com.example.springcore.controller.impl;

import com.example.springcore.controller.TraineeApi;
import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainerListToUpdateRequestDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TraineeTrainingMapper;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainingService;
import com.example.springcore.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TraineeControllerImpl implements TraineeApi {
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;

    private final TraineeTrainingMapper traineeTrainingMapper;

    @Timed(value = "create.trainee.time", description = "Time taken to create trainee")
    @Override
    public ResponseEntity<UserCredentialsDTO> createTrainee(TraineeDTO traineeDTO) {
        return new ResponseEntity<>(traineeService.createTrainee(traineeDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TraineeWithTrainersDTO> getTraineeByUsername(@RequestParam(value = "username") String username) {
        TraineeWithTrainersDTO traineeByUsername = traineeService.getTraineeDTOByUsername(username);
        return new ResponseEntity<>(traineeByUsername, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TraineeWithTrainersDTO> updateTrainee(TraineeDTO traineeDTO) {
        TraineeWithTrainersDTO traineeWithTrainersDTO = traineeService.updateTrainee(traineeDTO);
        return new ResponseEntity<>(traineeWithTrainersDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTrainee(String username) {
        traineeService.deleteTrainee(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(String username) {
        List<TrainerDTO> trainersNotAssignedToTrainee = traineeService.getTrainersNotAssignedToTrainee(username);
        return new ResponseEntity<>(trainersNotAssignedToTrainee, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainerDTO>> updateTraineesTrainersList(TraineeWithTrainerListToUpdateRequestDTO traineeWithTrainerListToUpdateRequestDTO) {
        List<TrainerDTO> trainerDTOS = traineeService.updateTrainersListInTraineeByUsername(traineeWithTrainerListToUpdateRequestDTO);
        return new ResponseEntity<>(trainerDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingsByCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String trainerUsername,
            String trainingTypeName
    ) {
        List<TrainingDTO> traineeTrainingsByCriteria = traineeTrainingMapper.
                fromTrainingListToTraineeTrainingListDTO(
                        trainingService.getTraineeTrainingsByCriteria(
                                username,
                                fromDate,
                                toDate,
                                trainerUsername,
                                trainingTypeName
                        )
                );
        return new ResponseEntity<>(traineeTrainingsByCriteria, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> changeTraineeStatus(String username, boolean isActive) {
        userService.changeUserStatus(username, isActive);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
