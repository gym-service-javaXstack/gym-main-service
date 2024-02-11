package com.example.springcore.service;

import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingDao trainingDao;

    @Autowired
    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training createTraining(Training training) {
        Training createdTraining = trainingDao.createTraining(training);
        logger.info("Created training: {}", createdTraining.getTrainingId());
        return createdTraining;
    }

    public Training getTraining(int trainingId) {
        Training training = trainingDao.getTraining(trainingId);
        logger.info("Retrieved training: {}", trainingId);
        return training;
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = trainingDao.getAll();
        logger.info("Retrieved all trainings");
        return trainings;
    }
}
