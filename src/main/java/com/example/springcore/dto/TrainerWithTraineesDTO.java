package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TrainerWithTraineesDTO extends TrainerDTO{

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TraineeDTO> trainees;

}
