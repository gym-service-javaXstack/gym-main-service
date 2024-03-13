package com.example.springcore.controller;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Training service", description = "Training management API")
@RequestMapping("/api/v1/training")
public interface TrainingApi {

    @Operation(summary = "Create new training ")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Training to be created", required = true,
            content = @Content(schema = @Schema(implementation = TrainingDTO.class)))
    @ApiResponse(responseCode = "201")
    @PostMapping
    ResponseEntity<Void> createTraining(@Valid @RequestBody TrainingDTO trainingDTO);

    @Operation(summary = "Get list of training types")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TrainingTypeDTO.class))
            ))
    @GetMapping("/type")
    ResponseEntity<List<TrainingTypeDTO>> getTrainingTypeList();
}
