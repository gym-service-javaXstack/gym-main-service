package com.example.springcore.controller;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/trainee")
public interface TraineeApi {

    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @GetMapping
    ResponseEntity<TraineeWithTrainersDTO> getTraineeByUsername(@RequestParam String username);

    @PutMapping
    ResponseEntity<TraineeWithTrainersDTO> updateTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @DeleteMapping
    ResponseEntity<Void> deleteTrainee(@RequestParam String username);

    @GetMapping("/available-trainers")
    ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(@RequestParam String username);
}
