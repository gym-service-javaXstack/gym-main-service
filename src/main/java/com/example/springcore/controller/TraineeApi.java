package com.example.springcore.controller;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.dto.request.UpdateTraineesTrainersListRequestDTO;
import com.example.springcore.exceptions.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @PostMapping
    ResponseEntity<UserCredentialsDTO> createTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Get trainee by username", tags = {"Trainee service"},
            description = "This can only be executed after authentication",
            parameters = {
                    @Parameter(name = "username", description = "The username that needs to be fetched", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TraineeWithTrainersDTO.class))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Specified wrong fields",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trainee with this username doesnt found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            })
    @GetMapping
    ResponseEntity<TraineeWithTrainersDTO> getTraineeByUsername(@RequestParam String username);

    @Operation(summary = "Update trainee", tags = {"Trainee service"}, description = "This can only be executed after authentication",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details to update trainee", required = true,
                    content = @Content(schema = @Schema(implementation = TraineeDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TraineeWithTrainersDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Specified wrong fields",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trainee with this username doesnt found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            }
    )
    @PutMapping
    ResponseEntity<TraineeWithTrainersDTO> updateTrainee(@Valid @RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Delete trainee", tags = {"Trainee service"},
            description = "This can only be executed after authentication",
            parameters = @Parameter(name = "username", description = "The username that needs to be fetched", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee deleted successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Specified wrong fields",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trainee with this username doesnt found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            }
    )
    @DeleteMapping
    ResponseEntity<Void> deleteTrainee(@RequestParam String username);

    @Operation(summary = "Get trainers not assigned to trainee", tags = {"Trainee service"},
            description = "This can only be executed after authentication",
            parameters = @Parameter(name = "username", description = "The username that needs to be fetched", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TrainerDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Specified wrong fields",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trainee with this username doesnt found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            }
    )
    @GetMapping("/available-trainers")
    ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(@RequestParam String username);

    @Operation(summary = "Update trainee's trainers list", tags = {"Trainee service"},
            description = "This can only be executed after authentication",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainee with trainers details", required = true,
                    content = @Content(schema = @Schema(implementation = UpdateTraineesTrainersListRequestDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trainee's trainers list updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TrainerDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Specified wrong fields",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Trainee with this username doesnt found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Error.class)
                            )
                    )
            }
    )
    @PutMapping("/trainers")
    ResponseEntity<List<TrainerDTO>> updateTraineesTrainersList(@Valid @RequestBody UpdateTraineesTrainersListRequestDTO updateTraineesTrainersListRequestDTO);

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


    @PatchMapping
    ResponseEntity<Void> changeTraineeStatus(@RequestParam String username, @RequestParam boolean isActive);
}
