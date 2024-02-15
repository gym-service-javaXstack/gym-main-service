package com.example.springcore.service;

import com.example.springcore.model.Training;
import com.example.springcore.repository.impl.TrainingDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingDao trainingDao;

    public Training createTraining(Training training) {
        Training createdTraining = trainingDao.save(training);
        logger.info("Created training: {}", createdTraining.getTrainingId());
        return createdTraining;
    }

    public Optional<Training> getTraining(int trainingId) {
        Optional<Training> training = trainingDao.get(trainingId);
        training.ifPresent(t -> logger.info("Retrieved training: {}", trainingId));
        return training;
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = trainingDao.getAll();
        logger.info("Retrieved all trainings");
        return trainings;
    }
}
