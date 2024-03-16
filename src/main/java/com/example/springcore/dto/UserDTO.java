package com.example.springcore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "User userName (optional)")
    private String userName;

    @NotEmpty
    @Schema(description = "User firstName (required)")
    private String firstName;

    @NotEmpty
    @Schema(description = "User lastName (required)")
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "User isActive (optional)")
    private Boolean isActive;

}
