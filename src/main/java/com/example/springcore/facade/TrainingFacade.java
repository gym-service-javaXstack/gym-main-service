package com.example.springcore.facade;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingFacade {
    private static final Logger logger = LoggerFactory.getLogger(TrainingFacade.class);
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;

    @Autowired
    public TrainingFacade(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
    }

    public Trainer createTrainer(Trainer trainer) {
        Trainer newTrainer = trainerService.createTrainer(trainer);
        return newTrainer;
    }

    public Trainee createTrainee(Trainee trainee) {
        Trainee newTrainee = traineeService.createTrainee(trainee);
        return newTrainee;
    }

    public void deleteTrainee(Integer id) {
        traineeService.deleteTrainee(id);
    }

    public void createTrainingScenario(Integer trainerId, Integer traineeId, Training training) {
        Trainer trainer = trainerService.getTrainer(trainerId);
        if (trainer == null) {
            logger.error("Trainer with id {} not found", trainerId);
            throw new IllegalArgumentException("Trainer not found");
        }

        Trainee trainee = traineeService.getTrainee(traineeId);
        if (trainee == null) {
            logger.error("Trainee with id {} not found", traineeId);
            throw new IllegalArgumentException("Trainee not found");
        }

        Training newTraining = trainingService.createTraining(Training.builder()
                .traineeId(trainee.getUserId())
                .trainerId(trainer.getUserId())
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .trainingDate(training.getTrainingDate())
                .duration(training.getDuration())
                .build());

        logger.info("Created training scenario with trainer id {}, trainee id {}, and training id {}",
                trainerId, traineeId, newTraining.getTrainingId());
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    public List<Trainee> getAllTrainee() {
        return traineeService.getAllTrainees();
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }
}
