package com.example.springcore.facade;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingFacade {
    private static final Logger logger = LoggerFactory.getLogger(TrainingFacade.class);
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;

    public Trainer createTrainer(Trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    public Trainee createTrainee(Trainee trainee) {
        return traineeService.createTrainee(trainee);
    }

    public void deleteTrainee(Integer id) {
        traineeService.deleteTrainee(id);
    }

    public void createTrainingScenario(Integer trainerId, Integer traineeId, Training training) {
        Trainer trainer = trainerService.getTrainer(trainerId)
                .orElseThrow(() -> {
                    logger.error("Trainer with id {} not found", trainerId);
                    return new IllegalArgumentException("Trainer not found");
                });

        Trainee trainee = traineeService.getTrainee(traineeId)
                .orElseThrow(() -> {
                    logger.error("Trainee with id {} not found", traineeId);
                    return new IllegalArgumentException("Trainee not found");
                });

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
