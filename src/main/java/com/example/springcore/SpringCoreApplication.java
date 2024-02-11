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

        System.out.println("----------------------------------ALL TRAINERS----------------------------------------------");
        List<Trainer> allTrainers = trainingFacade.getAllTrainers();

        allTrainers.forEach((a)-> System.out.println(a));
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("-----------------------------CREATING TRAINEE-------------------------------");


        trainingFacade.createTrainee(Trainee.builder()
                .firstName("TRAINEE")
                .lastName("CREATED")
                .isActive(true)
                .address("123 Main St")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build());

        System.out.println("------------------------------------------------------------------------");


        System.out.println("---------------------------------ALL TRAINEES---------------------------------------");

        List<Trainee> allTrainee = trainingFacade.getAllTrainee();

        allTrainee.forEach((a)-> System.out.println(a));

        System.out.println("------------------------------------------------------------------------");



        System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
        trainingFacade.createTrainingScenario(4, 4, Training.builder()
                .trainingName("Test Training")
                .trainingType( new TrainingType("KICKBOXING"))
                .trainingDate(LocalDate.now())
                .duration(60)
                .build());
        System.out.println("--------------------------------------------------------------------------------");


        System.out.println("-------------------------------ALL TRAININGS-----------------------------------------");
        List<Training> allTrainings = trainingFacade.getAllTrainings();

        allTrainings.forEach((a)-> System.out.println(a));

        System.out.println("------------------------------------------------------------------------");
    }
}
