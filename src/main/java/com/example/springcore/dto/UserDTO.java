package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String userName;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean isActive;
}
