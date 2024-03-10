package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TrainingTypeDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long id;

    @NotEmpty
    private String trainingTypeName;

}
