package com.example.springcore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TrainingTypeDTO {

    @NotEmpty
    private String trainingTypeName;

}
