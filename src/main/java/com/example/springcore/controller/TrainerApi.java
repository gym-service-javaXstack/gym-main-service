package com.example.springcore.controller;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/trainer")
public interface TrainerApi {

    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainer(@Valid @RequestBody TrainerDTO trainerDTO);

    @GetMapping
    ResponseEntity<TrainerWithTraineesDTO> getTrainerByUsername(@Valid @RequestParam(value = "username") String username);

    @PutMapping
    ResponseEntity<TrainerWithTraineesDTO> updateTrainer(@Valid @RequestBody TrainerDTO trainerDTO);
}
