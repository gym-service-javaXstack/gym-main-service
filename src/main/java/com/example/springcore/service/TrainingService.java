package com.example.springcore.service;

import com.example.springcore.model.Training;
import com.example.springcore.repository.impl.TrainingDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingDao trainingDao;

    public Training createTraining(Training training) {
        Training createdTraining = trainingDao.save(training);
        log.info("Created training: {}", createdTraining.getTrainingId());
        return createdTraining;
    }

    public Optional<Training> getTraining(int trainingId) {
        Optional<Training> training = trainingDao.get(trainingId);
        training.ifPresent(t -> log.info("Retrieved training: {}", trainingId));
        return training;
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = trainingDao.getAll();
        log.info("Retrieved all trainings");
        return trainings;
    }
}
