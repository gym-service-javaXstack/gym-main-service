package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TrainingTypeDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotEmpty
    @Schema(description = "trainingTypeName (required)")
    private String trainingTypeName;
}
