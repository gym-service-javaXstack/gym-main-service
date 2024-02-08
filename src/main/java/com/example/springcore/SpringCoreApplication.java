package com.example.springcore;

import com.example.springcore.model.Trainer;
import com.example.springcore.service.TrainerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        TrainerService trainerService = context.getBean(TrainerService.class);

        System.out.println("All trainers after initialization:");
        for (Trainer trainer : trainerService.getAllTrainers()) {
            System.out.println(trainer.toString());
        }
        Trainer trainer = trainerService.getTrainer(4);
        System.out.println("Get trainer with id = " + trainer.getUserId() + " " + trainer);
    }
}
