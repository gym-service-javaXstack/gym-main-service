package com.example.springcore.dto.request;

import com.example.springcore.dto.TrainingTypeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTrainerRequestDTO {

    @NotEmpty
    @Schema(description = "Trainer firstName (required)")
    private String firstName;

    @NotEmpty
    @Schema(description = "Trainer lastName (required)")
    private String lastName;

    @Valid
    @NotNull
    TrainingTypeDTO specialization;

}
