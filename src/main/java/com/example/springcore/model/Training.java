package com.example.springcore.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private Integer trainingId;
    private Integer traineeId;
    private Integer trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private LocalDate trainingDate;
    private Integer duration;
}
