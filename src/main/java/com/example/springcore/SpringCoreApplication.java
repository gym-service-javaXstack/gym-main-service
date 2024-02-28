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

        TrainingFacade trainingFacade = context.getBean(TrainingFacade.class);
        TrainingTypeDao trainingTypeDao = context.getBean(TrainingTypeDao.class);


        //todo:: CREATING TRAINER
        System.out.println("----------------------------------STEP 1: CREATING TRAINER----------------------------------------------");

        User userTrainer = User.builder()
                .firstName("John")
                .lastName("Trainer")
                .isActive(false)
                .build();
        System.out.println("--------------------------------------------------------------------------------");
        TrainingType trainingTypeForTrainer = trainingTypeDao.findTrainingTypeByName("Boxing");

        System.out.println("--------------------------------------------------------------------------------");

        System.out.println(trainingTypeForTrainer);

        System.out.println("--------------------------------------------------------------------------------");

        Trainer trainerCreatedForTest = trainingFacade.createTrainer(Trainer.builder()
                .user(userTrainer)
                .specialization(trainingTypeForTrainer)
                .build());

        System.out.println("---------------------------------STEP 1: END---------------------------------------------");

        //todo:: CREATING TRAINEE
        System.out.println("----------------------------------STEP 2: CREATING TRAINEE----------------------------------------------");
        User userTrainee = User.builder()
                .firstName("Ivan")
                .lastName("Trainee")
                .isActive(false)
                .build();

        Trainee traineeCreatedForTest = trainingFacade.createTrainee(Trainee.builder()
                .user(userTrainee)
                .dateOfBirth(LocalDate.now())
                .address("Home street")
                .build());

        System.out.println("-----------------------------------STEP 2: END---------------------------------------------");

        //todo:: AUTHENTICATE BY USERNAME
        System.out.println("----------------------------------STEP 3-4: authenticationUser----------------------------------------------");
        System.out.println(trainingFacade.authenticationUser(
                traineeCreatedForTest.getUser().getUserName(),
                traineeCreatedForTest.getUser().getPassword())
        );
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(trainingFacade.authenticationUser(
                trainerCreatedForTest.getUser().getUserName(),
                trainerCreatedForTest.getUser().getPassword())
        );
        System.out.println("-------------------------------------STEP 3-4: END----------------------------------------");

        //todo:: SELECT TRAINEE/TRAINER with linked Entities(Trainee+User)/(Trainer+User+TrainingType)
        System.out.println("----------------------------------STEP 5-6: Select Trainer/Trainee profile----------------------------------------------");

        Optional<Trainee> traineeByUsername = trainingFacade.getTraineeByUsername(traineeCreatedForTest.getUser().getUserName());
        System.out.println(traineeByUsername);
        System.out.println("--------------------------------------------------------------------------------");
        Optional<Trainer> trainerByUsername = trainingFacade.getTrainerByUsername(trainerCreatedForTest.getUser().getUserName());
        System.out.println(trainerByUsername);

        System.out.println("-----------------------------------STEP 5-6: END---------------------------------------------");

        //todo:: CHANGING PASSWORD by username(working both to Trainee/Trainer without selecting inner linked Entities)
        System.out.println("----------------------------------STEP 7-8: Change password Trainee/Trainer----------------------------------------------");
        System.out.println("-----------------------------------Changing password to traineeCreatedForTest by 1234567890---------------------------------------------");

        trainingFacade.changeUserPassword(
                traineeByUsername.get().getUser().getUserName(),
                traineeByUsername.get().getUser().getPassword(),
                "1234567890");

        System.out.println("-----------------------------------Changing password to trainerCreatedForTest by 0987654321---------------------------------------------");

        trainingFacade.changeUserPassword(
                trainerByUsername.get().getUser().getUserName(),
                trainerByUsername.get().getUser().getPassword(),
                "0987654321");

        System.out.println("------------------------------------STEP 7-8: END--------------------------------------------");


        //todo:: Updating Trainer
 /*       System.out.println("-----------------------------------STEP 9-10: Updating Trainee/Trainer profile---------------------------------------------");
        trainerByUsername.get().getUser().setFirstName("Neytan");
        traineeByUsername.get().getUser().setFirstName("TanTan");
        System.out.println("--------------------------------------------------------------------------------");
        trainingFacade.updateTrainer(trainerByUsername.get());
        trainingFacade.updateTrainee(traineeByUsername.get());
        System.out.println("-----------------------------------STEP 9-10: END---------------------------------------------");
*/
        //todo:: ACTIVATE/DEACTIVATE user's isActive field
/*        System.out.println("------------------------------------STEP 11-12: ACTIVATE/DEACTIVATE user--------------------------------------------");
        trainingFacade.changeUserStatus(trainerByUsername.get().getUser().getUserName(), true);
        System.out.println("--------------------------------------------------------------------------------");
        trainingFacade.changeUserStatus(traineeByUsername.get().getUser().getUserName(), true);
        System.out.println("-----------------------------------STEP 11-12: END---------------------------------------------");
        */
        //todo: DELETE with Trainee if u try to delete Trainer nothing happens via Trainer data in DB
/*
        System.out.println("----------------------------------DELETE TRAINEE----------------------------------------------");
        trainingFacade.deleteTrainee("Ivan.Trainee");
        System.out.println("--------------------------------------------------------------------------------");
*/

        //todo:: CREATING TRAINING
   /*     System.out.println("-------------------------------CREATING TRAINING-----------------------------------------");
        trainingFacade.createTraining(
                traineeByUsername.get(),
                trainerByUsername.get(),
                "Test11 Training123123",
                trainerByUsername.get().getSpecialization(),
                LocalDate.now().plusDays(14),
                60);

        System.out.println("---------------------------------END-----------------------------------------------");
*/
        //todo:: DELETING Trainee(cascading deleted Training)

        /*    trainingFacade.deleteTrainee("Ivan.Trainee2");*/

        //todo:: Get Trainers not assigned to Trainee with username
/*
        System.out.println("-------------------------------GET TRAINERS NOT ASSIGNED TO TRAINEE-----------------------------------------");
        List<Trainer> trainersNotAssignedToTrainee = trainingFacade.getTrainersNotAssignedToTrainee("Ivan.Trainee2");
        System.out.println(trainersNotAssignedToTrainee);
        System.out.println("-------------------------------END-----------------------------------------");
*/

        //todo:: GET Trainee Trainings List by trainee username and criteria (from date, to date, trainer name, training type)
/*
        System.out.println("-------------------------------GET Trainee Trainings List by trainee username and criteria-----------------------------------------");
        List<Training> traineeTrainingsByCriteria = trainingFacade.getTraineeTrainingsByCriteria(
                "Ivan.Trainee1",
                LocalDate.now(),
                LocalDate.now().plusDays(14),
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
