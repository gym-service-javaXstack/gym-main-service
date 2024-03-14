package com.example.springcore.controller;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.dto.UserDTO;
import com.example.springcore.dto.request.CreateTrainerRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Trainer service", description = "Trainer management API")
@RequestMapping("/api/v1/trainer")
public interface TrainerApi {

    @Operation(summary = "Create a new trainer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainer details", required = true,
            content = @Content(schema = @Schema(implementation = CreateTrainerRequestDTO.class)))
    @ApiResponse(responseCode = "201", description = "Trainer created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserCredentialsDTO.class))
    )
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

    @Operation(summary = "Change trainer status")
    @ApiResponse(responseCode = "200", description = "Trainer status changed successfully")
    @PatchMapping
    ResponseEntity<Void> changeTrainerStatus(@RequestParam UserDTO userDTO);
}
