package com.example.springcore.service;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.repository.TraineeDao;
import com.example.springcore.repository.TrainerDao;
import com.example.springcore.repository.TrainingDao;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingDao trainingDao;
    private final TraineeDao traineeDao;
    private final TrainerDao trainerDao;
    private final TraineeService traineeService;

    @Transactional
    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Enter TrainingService createTraining");

        Trainee traineeByUsername = traineeDao.getTraineeByUsername(trainingDTO.getTraineeUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + trainingDTO.getTraineeUserName() + " not found"));

        Trainer trainerByUsername = trainerDao.getTrainerByUsername(trainingDTO.getTrainerUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainer with username " + trainingDTO.getTrainerUserName() + " not found"));

        traineeService.linkTraineeAndTrainee(traineeByUsername, trainerByUsername);

        Training training = Training.builder()
                .trainee(traineeByUsername)
                .trainer(trainerByUsername)
                .trainingName(trainingDTO.getTrainingName())
                .trainingType(trainerByUsername.getSpecialization())
                .trainingDate(trainingDTO.getTrainingDate())
                .duration(trainingDTO.getDuration())
                .build();

        trainingDao.save(training);
        log.info("Exit TrainingService createTraining: {}", training);
    }
}
