package com.example.springcore.controller.impl;

import com.example.springcore.controller.TrainerApi;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainer;
import com.example.springcore.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrainerControllerImpl implements TrainerApi {
    private final TrainerService trainerService;

    @Override
    public ResponseEntity<UserCredentialsDTO> createTrainer(TrainerDTO trainerDTO) {
        Trainer trainer = trainerService.createTrainer(trainerDTO);
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername(trainer.getUser().getUserName());
        userCredentialsDTO.setPassword(trainer.getUser().getPassword());
        return new ResponseEntity<>(userCredentialsDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> getTrainerByUsername(String username) {
        TrainerWithTraineesDTO trainerByUsername = trainerService.getTrainerByUsername(username);
        return new ResponseEntity<>(trainerByUsername, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TrainerWithTraineesDTO> updateTrainer(TrainerDTO trainerDTO) {
        TrainerWithTraineesDTO trainerWithTraineesDTO = trainerService.updateTrainer(trainerDTO);
        return new ResponseEntity<>(trainerWithTraineesDTO, HttpStatus.OK);
    }
}
