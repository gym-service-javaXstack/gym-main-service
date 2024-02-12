package com.example.springcore.util;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;

import java.time.LocalDate;

public class TestUtil {
    public static Trainee createTestTrainee() {
        return Trainee.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("username")
                .password("password")
                .isActive(true)
                .userId(1)
                .address("Address")
                .dateOfBirth(LocalDate.now())
                .build();
    }

    public static Trainer createTestTrainer() {
        return Trainer.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("username")
                .password("password")
                .isActive(true)
                .userId(1)
                .specialization(new TrainingType("Specialization"))
                .build();
    }

    public static Training createTestTraining() {
        return Training.builder()
                .trainingId(1)
                .traineeId(1)
                .trainerId(1)
                .trainingName("TrainingName")
                .trainingType(new TrainingType("TrainingType"))
                .trainingDate(LocalDate.now())
                .duration(1)
                .build();
    }
}
