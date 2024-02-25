package com.example.springcore;

import com.example.springcore.facade.TrainingFacade;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.impl.TrainingTypeDao;
import com.example.springcore.service.AuthenticationService;
import com.example.springcore.service.ProfileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        TrainingFacade trainingFacade  = context.getBean(TrainingFacade.class);

        System.out.println("----------------------------------authenticationUser----------------------------------------------");


        System.out.println(authenticationService.authenticationUser("Ivan.Trainee", "12345678"));
        System.out.println(authenticationService.authenticationUser("Andriy.Trainer", "1112345"));

        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("------------------------------------ACTIVATE/DEACTIVATE user--------------------------------------------");
        trainingFacade.changeTraineeStatus("Ivan.Trainee", false);

        System.out.println("--------------------------------------------------------------------------------");

//        System.out.println("----------------------------------GET USER----------------------------------------------");
//
//        System.out.println(trainingFacade.getTraineeByUsername("Ivan.Trainee"));
//        System.out.println(trainingFacade.getTrainerByUsername("Andriy.Trainer"));
//
//        System.out.println("--------------------------------------------------------------------------------");


//        System.out.println("----------------------------------CHANGE PASSWORD TRAINEE/TRAINER----------------------------------------------");
//        System.out.println(trainingFacade.getTrainerByUsername("Andriy.Trainer"));
//        System.out.println("--------------------------------------------------------------------------------");
//        Trainer trainer = trainingFacade.changeTrainerPassword("Andriy.Trainer", "1112345");
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println(trainer);
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println(trainingFacade.getTrainerByUsername("Andriy.Trainer"));
//        System.out.println("--------------------------------------------------------------------------------");


//        System.out.println("----------------------------------CREATING TRAINER----------------------------------------------");
//
//        User userTrainer = User.builder()
//                .firstName("Andriy")
//                .lastName("Trainer")
//                .isActive(true)
//                .build();
//
//        TrainingType trainingTypeForTrainer = trainingTypeDao.findTrainingTypeByName("Test");
//
//        System.out.println(trainingTypeForTrainer);
//
//
//        trainingFacade.createTrainer(Trainer.builder()
//                .user(userTrainer)
//                .specialization(trainingTypeForTrainer)
//                .build());
//        System.out.println("--------------------------------------------------------------------------------");
//
//
//        System.out.println("----------------------------------CREATING TRAINEE----------------------------------------------");
//        User userTrainee = User.builder()
//                .firstName("Ivan")
//                .lastName("Trainee")
//                .isActive(true)
//                .build();
//
//        trainingFacade.createTrainee(Trainee.builder()
//                .user(userTrainee)
//                .dateOfBirth(LocalDate.now())
//                .address("Home street")
//                .build());
//
//        System.out.println("--------------------------------------------------------------------------------");
//
//        System.out.println("----------------------------------DELETE TRAINEE----------------------------------------------");
//        trainingFacade.deleteTrainee("Andriy.Trainer6");
//        System.out.println("--------------------------------------------------------------------------------");

//        System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
//        trainingFacade.createTrainingScenario(6, 5, Training.builder()
//                .trainingName("Test Training")
//                .trainingType(new TrainingType(26L, "KICKBOXING"))
//                .trainingDate(LocalDate.now())
//                .duration(60)
//                .build());
//        System.out.println("--------------------------------------------------------------------------------");
    }
}
