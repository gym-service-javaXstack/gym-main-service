package com.example.springcore.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class TraineeWithTrainerListToUpdateRequestDTO {

    @NotEmpty
    private String userName;

    @NotEmpty
    private List<TrainerUsername> trainers;

    @Data
    @AllArgsConstructor
    public static class TrainerUsername {
        @NotEmpty
        private String userName;
    }
}
