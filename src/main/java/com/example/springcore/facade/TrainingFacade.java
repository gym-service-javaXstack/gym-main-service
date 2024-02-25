package com.example.springcore.facade;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.User;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingFacade {
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;

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
        return traineeService.getByUsername(username);
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        return trainerService.getByUsername(username);
    }

    public Trainee updateTrainee(Trainee trainee){
        return traineeService.updateTrainee(trainee);
    }
    public Trainer updateTrainer(Trainer trainer){
        return trainerService.updateTrainer(trainer);
    }

    public Trainee changeTraineePassword(String username, String newPassword){
        return traineeService.changeTraineePassword(username, newPassword);
    }
    public Trainer changeTrainerPassword(String username, String newPassword){
        return trainerService.changeTrainerPassword(username, newPassword);
    }

    public void changeTrainerStatus(String username, boolean isActive) {
        trainerService.changeTrainerStatus(username, isActive);
    }
    public void changeTraineeStatus(String username, boolean isActive) {
        traineeService.changeTraineeStatus(username, isActive);
    }
}
