package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TraineeWithTrainersDTO extends TraineeDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TrainerDTO> trainers;

}
