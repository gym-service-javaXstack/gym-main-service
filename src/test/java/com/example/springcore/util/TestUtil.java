package com.example.springcore.util;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;

import java.time.LocalDate;
import java.util.HashSet;

public class TestUtil {

    public static User createUser(String username, String password) {
        return User.builder()
                .userName(username)
                .password(password)
                .firstName("firstName")
                .lastName("lastName")
                .isActive(true)
                .build();
    }

    public static Trainer createTrainer(User user, TrainingType trainingType) {
        return Trainer.builder()
                .user(user)
                .specialization(trainingType)
                .build();
    }

    public static Trainee createTrainee(User user, Trainer trainer) {
        Trainee trainee = Trainee.builder()
                .user(user)
                .trainers(new HashSet<>())
                .build();
        if (trainer != null) {
            trainee.addTrainer(trainer);
        }
        return trainee;
    }

    public static Training createTraining(Trainee trainee, Trainer trainer, TrainingType trainingType) {
        return Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingType(trainingType)
                .trainingName("trainingName")
                .trainingDate(LocalDate.now())
                .duration(60)
                .build();
    }

    public static TrainingType createTrainingType(String trainingTypeName) {
        return TrainingType.builder()
                .trainingTypeName(trainingTypeName)
                .build();
    }
}
