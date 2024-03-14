package com.example.springcore.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateTraineesTrainersListRequestDTO {

    @NotEmpty
    @Schema(description = "Trainee userName (required)")
    private String userName;

    @NotEmpty
    private List<TrainerUsername> trainers;

    @Data
    @Schema(description = "Trainee List of Trainers userNames that must be updated (required)")
    public static class TrainerUsername {

        @NotEmpty
        @Schema(description = "Trainer userName (required)")
        private String userName;
    }
}
