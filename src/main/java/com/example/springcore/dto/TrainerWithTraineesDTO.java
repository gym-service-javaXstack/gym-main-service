package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TrainerWithTraineesDTO extends TrainerDTO{

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TraineeDTO> trainees;

}
