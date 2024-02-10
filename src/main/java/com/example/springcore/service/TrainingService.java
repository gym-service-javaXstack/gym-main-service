package com.example.springcore.service;

import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private final TrainingDao trainingDao;

    @Autowired
    public TrainingService(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training createTraining(Training training) {
        return trainingDao.createTraining(training);
    }

    public Training getTraining(int trainingId) {
        return trainingDao.getTraining(trainingId);
    }

    public List<Training> getAllTrainings() {
        return trainingDao.getAll();
    }
}
