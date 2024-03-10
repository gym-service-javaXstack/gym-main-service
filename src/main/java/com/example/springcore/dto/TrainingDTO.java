package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDTO {

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String traineeUserName;

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String trainerUserName;

    @NotEmpty
    String trainingName;

    @NotNull
    LocalDate trainingDate;

    @NotNull
    Integer duration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String trainingTypeName;
}
