package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String userName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean isActive;

}
