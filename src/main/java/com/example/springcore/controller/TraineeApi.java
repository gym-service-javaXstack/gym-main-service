package com.example.springcore.controller;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Trainee service", description = "Trainee management API")
@RequestMapping("/api/v1/trainee")
public interface TraineeApi {

    @Operation(summary = "Create a new trainee")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainee details", required = true,
            content = @Content(schema = @Schema(implementation = TraineeDTO.class)))
    @ApiResponse(responseCode = "201", description = "Trainee created successfully",
            content = @Content(schema = @Schema(implementation = UserCredentialsDTO.class)))
    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Get trainee by username")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(schema = @Schema(implementation = TraineeWithTrainersDTO.class)))
    @GetMapping
    ResponseEntity<TraineeWithTrainersDTO> getTraineeByUsername(@RequestParam String username);

    @Operation(summary = "Update trainee")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New trainee details", required = true,
            content = @Content(schema = @Schema(implementation = TraineeDTO.class)))
    @ApiResponse(responseCode = "200", description = "Trainee updated successfully",
            content = @Content(schema = @Schema(implementation = TraineeWithTrainersDTO.class)))
    @PutMapping
    ResponseEntity<TraineeWithTrainersDTO> updateTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Delete trainee")
    @ApiResponse(responseCode = "200", description = "Trainee deleted successfully")
    @DeleteMapping
    ResponseEntity<Void> deleteTrainee(@RequestParam String username);

    @Operation(summary = "Get trainers not assigned to trainee")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainerDTO.class))))
    @GetMapping("/available-trainers")
    ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(@RequestParam String username);

    @Operation(summary = "Update trainee's trainers list")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainee with trainers details", required = true,
            content = @Content(schema = @Schema(implementation = TraineeWithTrainersDTO.class)))
    @ApiResponse(responseCode = "200", description = "Trainee's trainers list updated successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainerDTO.class))))
    @PutMapping("/trainers")
    ResponseEntity<List<TrainerDTO>> updateTraineesTrainersList(@RequestBody TraineeWithTrainersDTO traineeWithTrainersDTO);

    @Operation(summary = "Get trainee trainings by criteria")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrainingDTO.class))))
    @GetMapping("/trainings")
    ResponseEntity<List<TrainingDTO>> getTraineeTrainingsByCriteria(
            @RequestParam String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String trainerUsername,
            @RequestParam(required = false) String trainingTypeName);

    @Operation(summary = "Change trainee status")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true,
            content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "200", description = "Trainee status changed successfully")
    @PatchMapping
    ResponseEntity<Void> changeTraineeStatus(@RequestBody UserDTO userDTO);
}
