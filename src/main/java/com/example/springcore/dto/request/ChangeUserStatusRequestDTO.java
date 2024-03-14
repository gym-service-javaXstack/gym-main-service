package com.example.springcore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeUserStatusRequestDTO {

    @Schema(description = "User userName (required)")
    @NotEmpty
    private String userName;

    @Schema(description = "User isActive to update (required)")
    @NotNull
    private Boolean isActive;

}
