package com.example.springcore.controller;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/trainer")
public interface TrainerApi {

    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainer(@Valid @RequestBody TrainerDTO trainerDTO);

    @GetMapping
    ResponseEntity<TrainerWithTraineesDTO> getTrainerByUserName(@RequestParam String username);

    @PutMapping
    ResponseEntity<TrainerWithTraineesDTO> updateTrainer(@Valid @RequestBody TrainerDTO trainerDTO);

    @GetMapping("/trainings")
    ResponseEntity<List<TrainingDTO>> getTrainerTrainingsByCriteria(
            @RequestParam String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String traineeUsername);

    @PatchMapping
    ResponseEntity<Void> changeTrainerStatus(@RequestParam String username, @RequestParam boolean isActive);

    @GetMapping("/workload")
    ResponseEntity<Integer> getTrainerSummaryByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") @Min(1) @Max(12) int monthValue,
            @NotNull @RequestHeader("Authorization") String authHeader
    );
}
