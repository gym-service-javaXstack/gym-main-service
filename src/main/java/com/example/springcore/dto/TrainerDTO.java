package com.example.springcore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainerDTO extends UserDTO{

    @Valid
    @NotNull
    private TrainingTypeDTO specialization;

}
