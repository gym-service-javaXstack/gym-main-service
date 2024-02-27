package com.example.springcore.facade;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import com.example.springcore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingFacade {
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;

    public Trainer createTrainer(Trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    public Trainee createTrainee(Trainee trainee) {
        return traineeService.createTrainee(trainee);
    }

    public void deleteTrainee(String username) {
        traineeService.deleteTrainee(username);
    }

//    public void createTrainingScenario(Integer trainerId, Integer traineeId, Training training) {
//        Trainer trainer = trainerService.getTrainer(trainerId)
//                .orElseThrow(() -> {
//                    logger.error("Trainer with id {} not found", trainerId);
//                    return new IllegalArgumentException("Trainer not found");
//                });
//
//        Trainee trainee = traineeService.getTrainee(traineeId)
//                .orElseThrow(() -> {
//                    logger.error("Trainee with id {} not found", traineeId);
//                    return new IllegalArgumentException("Trainee not found");
//                });
//
//        Training newTraining = trainingService.createTraining(Training.builder()
//                .trainer(trainer)
//                .trainee(trainee)
//                .trainingName(training.getTrainingName())
//                .trainingType(training.getTrainingType())
//                .trainingDate(training.getTrainingDate())
//                .duration(training.getDuration())
//                .build());
//
//        logger.info("Created training scenario with trainer id {}, trainee id {}, and training id {}",
//                trainerId, traineeId, newTraining.getTrainingId());
//    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        return traineeService.getTraineeByUsername(username);
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        return trainerService.getTrainerByUsername(username);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.updateTrainee(trainee);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.updateTrainer(trainer);
    }


    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        userService.changeUserPassword(username, oldPassword, newPassword);
    }

    public void changeUserStatus(String username, boolean isActive) {
        userService.changeUserStatus(username, isActive);
    }

    public void createTraining(Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType, LocalDate trainingDate, Integer duration) {
        trainingService.createTraining(trainee, trainer, trainingName, trainingType, trainingDate, duration);
    }

    public List<Trainer> getTrainersNotAssignedToTrainee(String username) {
        return traineeService.getTrainersNotAssignedToTrainee(username);
    }

    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, TrainingType trainingType) {
        return traineeService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
    }

    public List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName) {
        return trainerService.getTrainerTrainingsByCriteria(username, fromDate, toDate, trainerName);
    }
}
