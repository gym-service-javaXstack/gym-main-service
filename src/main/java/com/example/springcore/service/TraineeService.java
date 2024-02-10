package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.TraineeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private final TraineeDao traineeDao;

    @Autowired
    public TraineeService(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    public Trainee createTrainee(Trainee trainee) {
        traineeDao.save(trainee);
        return trainee;
    }

    public Trainee updateTrainee(Trainee trainee) {
        traineeDao.save(trainee);
        return trainee;
    }

    public Trainee getTrainee(Integer id) {
        return traineeDao.get(id);
    }

    public List<Trainee> getAllTrainees() {
        return traineeDao.getAll();
    }

    public void deleteTrainee(Integer id) {
        traineeDao.delete(id);
    }
}
