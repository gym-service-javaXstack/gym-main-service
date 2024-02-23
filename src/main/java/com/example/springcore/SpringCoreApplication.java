package com.example.springcore;

import com.example.springcore.facade.TrainingFacade;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.impl.TrainingTypeDao;
import com.example.springcore.service.ProfileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);
        TrainingTypeDao trainingTypeDao = context.getBean(TrainingTypeDao.class);
        ProfileService profileService = context.getBean(ProfileService.class);

        System.out.println("----------------------------------checkUsernameAndPassword----------------------------------------------");

        System.out.println(profileService.authenticationUser("Ivan.Trainee", "yBpHn0T7zS"));
        System.out.println("--------------------------------------------------------------------------------");


        System.out.println("----------------------------------CREATING TRAINER----------------------------------------------");

        User userTrainer = User.builder()
                .firstName("Andriy")
                .lastName("Trainer")
                .isActive(true)
                .build();

        TrainingType trainingTypeForTrainer = trainingTypeDao.findTrainingTypeByName("Test");

        System.out.println(trainingTypeForTrainer);


        trainingFacade.createTrainer(Trainer.builder()
                .user(userTrainer)
                .specialization(trainingTypeForTrainer)
                .build());
        System.out.println("--------------------------------------------------------------------------------");


        System.out.println("----------------------------------CREATING TRAINEE----------------------------------------------");
        User userTrainee = User.builder()
                .firstName("Ivan")
                .lastName("Trainee")
                .isActive(true)
                .build();

        trainingFacade.createTrainee(Trainee.builder()
                .user(userTrainee)
                .dateOfBirth(LocalDate.now())
                .address("Home street")
                .build());

        System.out.println("--------------------------------------------------------------------------------");

//        System.out.println("----------------------------------DELETE TRAINEE----------------------------------------------");
//        trainingFacade.deleteTrainee("Ivan.Trainee");
//        System.out.println("--------------------------------------------------------------------------------");

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
