package com.example.springcore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTraineeRequestDTO {

    @NotEmpty
    @Schema(description = "Trainee firstName (required)")
    private String firstName;

    @NotEmpty
    @Schema(description = "Trainee lastName (required)")
    private String lastName;

    @Schema(description = "Trainee address (optional)")
    private String address;

    @Schema(description = "Trainee dateOfBirth (optional)")
    private LocalDate dateOfBirth;

}
