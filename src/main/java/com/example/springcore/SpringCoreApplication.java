package com.example.springcore;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        TrainerService trainerService = context.getBean(TrainerService.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);


        Trainer newTrainer = trainerService.createTrainer(
                new Trainer(
                        "NICK",
                        "TEST",
                        true,
                        5,
                        new TrainingType("KICKBOXING")));

        System.out.println("TEST---------- = " + newTrainer);

        System.out.println("All TRAINERS after initialization:");
        for (Trainer trainer : trainerService.getAllTrainers()) {
            System.out.println(trainer.toString());
        }

        System.out.println("All TRAINEE after initialization:");
        for (Trainee trainee : traineeService.getAllTrainees()) {
            System.out.println(trainee.toString());
        }

        System.out.println("All TRAINING after initialization:");
        for (Training training : trainingService.getAllTrainings()) {
            System.out.println(training.toString());
        }
    }
}
