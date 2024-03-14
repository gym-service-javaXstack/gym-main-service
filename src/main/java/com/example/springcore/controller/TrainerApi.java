package com.example.springcore.controller;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/trainer")
public interface TrainerApi {

    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainer(@Valid @RequestBody TrainerDTO trainerDTO);

    @Operation(summary = "Get trainer by username")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TrainerWithTraineesDTO.class))
    )
    @GetMapping
    ResponseEntity<TrainerWithTraineesDTO> getTrainerByUsername(@Valid @RequestParam(value = "username") String username);

    @Operation(summary = "Update trainer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New trainer details", required = true,
            content = @Content(schema = @Schema(implementation = TrainerDTO.class)))
    @ApiResponse(responseCode = "200", description = "Trainer updated successfully",
            content = @Content(schema = @Schema(implementation = TrainerWithTraineesDTO.class)))
    @PutMapping
    ResponseEntity<TrainerWithTraineesDTO> updateTrainer(@Valid @RequestBody TrainerDTO trainerDTO);

    @Operation(summary = "Get trainer trainings by criteria")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainingDTO.class))))
    @GetMapping("/trainings")
    ResponseEntity<List<TrainingDTO>> getTrainerTrainingsByCriteria(
            @RequestParam String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String traineeUsername);


    @PatchMapping
    ResponseEntity<Void> changeTrainerStatus(@RequestParam String username, @RequestParam boolean isActive);
}
