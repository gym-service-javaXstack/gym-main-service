package com.example.springcore;

import com.example.springcore.facade.TrainingFacade;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainingTypeDao;
import com.example.springcore.service.AuthenticationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringCoreApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);

        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
        TrainingTypeDao trainingTypeDao = context.getBean(TrainingTypeDao.class);
        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);

        //todo:: AUTHENTICATE BY USERNAME
        System.out.println("----------------------------------authenticationUser----------------------------------------------");

        System.out.println(authenticationService.authenticationUser("Ivan.Trainee", "1pUBtNyoKX"));
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(authenticationService.authenticationUser("Andriy.Trainers1", "w6HrP7SOwU"));

        System.out.println("----------------------------------------END----------------------------------------");

        //todo:: ACTIVATE/DEACTIVATE user's isActive field
/*        System.out.println("------------------------------------ACTIVATE/DEACTIVATE user--------------------------------------------");
        trainingFacade.changeUserStatus("Ivan.Trainee", false);
        System.out.println("--------------------------------------------------------------------------------");
        trainingFacade.changeUserStatus("Andriy.Trainer", false);
        System.out.println("-----------------------------------END---------------------------------------------");*/

        //todo:: GET TRAINEE/TRAINER with linked Entities(Trainee+User)/(Trainer+User+TrainingType)
        System.out.println("----------------------------------GET USER----------------------------------------------");

        Optional<Trainee> traineeByUsername = trainingFacade.getTraineeByUsername("Ivan.Trainee");
        System.out.println(traineeByUsername);
        System.out.println("--------------------------------------------------------------------------------");
        Optional<Trainer> trainerByUsername = trainingFacade.getTrainerByUsername("Andriy.Trainers1");
        System.out.println(trainerByUsername);

        System.out.println("-----------------------------------END---------------------------------------------");

        //todo:: CHANGING PASSWORD by username(working both to Trainee/Trainer without selecting inner linked Entities)
/*
      System.out.println("----------------------------------CHANGE PASSWORD TRAINEE/TRAINER----------------------------------------------");
        System.out.println(trainingFacade.getTrainerByUsername("Ivan.Trainee1"));
        System.out.println("--------------------------------------------------------------------------------");
        trainingFacade.changeUserPassword("Ivan.Trainee1", "1testPassword", "12345678");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(trainingFacade.getTrainerByUsername("Ivan.Trainee1"));
        System.out.println("------------------------------------END--------------------------------------------");
*/

        //todo:: CREATING TRAINER
 /*       System.out.println("----------------------------------CREATING TRAINER----------------------------------------------");

        User userTrainer = User.builder()
                .firstName("Andriy")
                .lastName("Trainers")
                .isActive(false)
                .build();
        System.out.println("--------------------------------------------------------------------------------");
        TrainingType trainingTypeForTrainer = trainingTypeDao.findTrainingTypeByName("Test");

        System.out.println("--------------------------------------------------------------------------------");

        System.out.println(trainingTypeForTrainer);

        System.out.println("--------------------------------------------------------------------------------");

        trainingFacade.createTrainer(Trainer.builder()
                .user(userTrainer)
                .specialization(trainingTypeForTrainer)
                .build());

        System.out.println("-----------------------------------END---------------------------------------------");
*/
        //todo:: CREATING TRAINEE

     /*   System.out.println("----------------------------------CREATING TRAINEE----------------------------------------------");
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
*/
        //todo: WORKING with Trainee if u try to delete Trainer nothing happens via Trainer data in DB
/*        System.out.println("----------------------------------DELETE TRAINEE----------------------------------------------");
        trainingFacade.deleteTrainee("Andriy.Trainer");
        System.out.println("--------------------------------------------------------------------------------");
    */

        //todo:: CREATING TRAINING
/*
        System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
        trainingFacade.createTraining(
                traineeByUsername.get(),
                trainerByUsername.get(),
                "Test Training",
                trainerByUsername.get().getSpecialization(),
                LocalDate.now(),
                60);

        System.out.println("---------------------------------END-----------------------------------------------");
*/

        //todo:: DELETING Trainee(cascading deleted Training)

     /*    trainingFacade.deleteTrainee("Ivan.Trainee2");*/

        //todo:: Get Trainers not assigned to Trainee with username
 /*       System.out.println("-------------------------------GET TRAINERS NOT ASSIGNED TO TRAINEE-----------------------------------------");
        List<Trainer> trainersNotAssignedToTrainee = trainingFacade.getTrainersNotAssignedToTrainee("Ivan.Trainee");
        System.out.println(trainersNotAssignedToTrainee);
        System.out.println("-------------------------------END-----------------------------------------");*/

        //todo:: GET Trainee Trainings List by trainee username and criteria (from date, to date, trainer name, training type)
   /*     System.out.println("-------------------------------GET Trainee Trainings List by trainee username and criteria-----------------------------------------");
        List<Training> traineeTrainingsByCriteria = trainingFacade.getTraineeTrainingsByCriteria(
                "Ivan.Trainee",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                "Andriy",
                trainerByUsername.get().getSpecialization()

        );
        System.out.println(traineeTrainingsByCriteria);
        System.out.println("-------------------------------END-----------------------------------------");
        */

        //todo:: GET Trainer Trainings List by trainer username and criteria (from date, to date, trainee name)
/*        System.out.println("-------------------------------GET Trainer Trainings List by trainer username and criteria-----------------------------------------");
        List<Training> trainerTrainingsByCriteria = trainingFacade.getTrainerTrainingsByCriteria(
                "Andriy.Trainers1",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                "Ivan"
        );
        System.out.println(trainerTrainingsByCriteria);
        System.out.println("-------------------------------END-----------------------------------------");*/
    }
}
