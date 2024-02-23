package com.example.springcore;

import com.example.springcore.facade.TrainingFacade;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);


//        System.out.println("----------------------------------CREATING TRAINER----------------------------------------------");
//        User userTrainer = User.builder()
//                .firstName("Trainer")
//                .lastName("Trainer")
//                .isActive(true)
//                .build();
//
//        TrainingType trainingTypeForTrainer = TrainingType.builder()
//                .trainingTypeName("Test training")
//                .build();
//
//        trainingFacade.createTrainer(Trainer.builder()
//                .user(userTrainer)
//                .specialization(trainingTypeForTrainer)
//                .build());
//        System.out.println("--------------------------------------------------------------------------------");


        System.out.println("----------------------------------CREATING TRAINEE----------------------------------------------");
        User userTrainee = User.builder()
                .firstName("Trainee")
                .lastName("Trainee")
                .isActive(true)
                .build();

        trainingFacade.createTrainee(Trainee.builder()
                .user(userTrainee)
                .dateOfBirth(LocalDate.now())
                .address("Home street")
                .build());

        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("----------------------------------DELETE TRAINEE----------------------------------------------");
        trainingFacade.deleteTrainee(1502);
        System.out.println("--------------------------------------------------------------------------------");

//        System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
//        trainingFacade.createTrainingScenario(6, 5, Training.builder()
//                .trainingName("Test Training")
//                .trainingType(new TrainingType(26L, "KICKBOXING"))
//                .trainingDate(LocalDate.now())
//                .duration(60)
//                .build());
//        System.out.println("--------------------------------------------------------------------------------");


//        System.out.println("-----------------------------ALL TRAINEE---------------------------------------------------");
//        List<Trainee> trainees = trainingFacade.getAllTrainee();
//        System.out.println("Number of trainees: " + trainees.size());
//        trainees.forEach(System.out::println);
//        System.out.println("--------------------------------------------------------------------------------");

//        System.out.println("-----------------------------ALL TRAINERS---------------------------------------------------");
//        trainingFacade.getAllTrainers().forEach(System.out::println);
//        System.out.println("--------------------------------------------------------------------------------");
//
//        System.out.println("-----------------------------ALL TRAINING---------------------------------------------------");
//        trainingFacade.getAllTrainings().forEach(System.out::println);
//        System.out.println("--------------------------------------------------------------------------------");
    }
}
