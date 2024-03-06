package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeDTO extends UserDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LocalDate dateOfBirth;

}
