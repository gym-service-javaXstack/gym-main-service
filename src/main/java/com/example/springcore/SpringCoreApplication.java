package com.example.springcore;

import com.example.springcore.facade.TrainingFacade;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);
        

        System.out.println("----------------------------------CREATING TRAINER----------------------------------------------");
        trainingFacade.createTrainer(Trainer.builder()
                .firstName("TRAINER")
                .lastName("CREATED")
                .isActive(true)
                .specialization( new TrainingType("TEST"))
                .build());
        System.out.println("--------------------------------------------------------------------------------");


        System.out.println("----------------------------------CREATING TRAINEE----------------------------------------------");
        trainingFacade.createTrainee(Trainee.builder()
                .firstName("TRAINER")
                .lastName("CREATED")
                .isActive(true)
                .dateOfBirth(LocalDate.now())
                .address("Home street")
                .build());
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
        trainingFacade.createTrainingScenario(6, 5, Training.builder()
                .trainingName("Test Training")
                .trainingType( new TrainingType("KICKBOXING"))
                .trainingDate(LocalDate.now())
                .duration(60)
                .build());
        System.out.println("--------------------------------------------------------------------------------");

    }
}
