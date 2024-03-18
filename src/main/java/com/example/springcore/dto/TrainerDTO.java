package com.example.springcore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TrainerDTO extends UserDTO {

    @NotNull
    private TrainingTypeDTO specialization;

}
