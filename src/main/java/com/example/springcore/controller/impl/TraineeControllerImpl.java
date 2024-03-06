package com.example.springcore.controller.impl;

import com.example.springcore.controller.TraineeApi;
import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TraineeControllerImpl implements TraineeApi {
    private final TraineeService traineeService;

    @Override
    public ResponseEntity<UserCredentialsDTO> createTrainee(@RequestBody TraineeDTO traineeDTO) {
        Trainee response = traineeService.createTrainee(traineeDTO);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername(response.getUser().getUserName());
        userCredentialsDTO.setPassword(response.getUser().getPassword());
        return new ResponseEntity<>(userCredentialsDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TraineeWithTrainersDTO> getTraineeByUsername(@RequestParam(value = "username") String username) {
        TraineeWithTrainersDTO traineeByUsername = traineeService.getTraineeByUsername(username);
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
}
