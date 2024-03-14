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
    private String traineeUserName;

    @NotEmpty
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String trainerUserName;

    @NotEmpty
    private String trainingName;

    @NotNull
    private LocalDate trainingDate;

    @NotNull
    private Integer duration;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String trainingTypeName;
}
