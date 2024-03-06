package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeWithTrainersDTO extends TraineeDTO{

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TrainerDTO> trainers;

}
