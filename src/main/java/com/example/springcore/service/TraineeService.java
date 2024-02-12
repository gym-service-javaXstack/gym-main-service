package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.TraineeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    private final TraineeDao traineeDao;
    private final ProfileService profileService;

    @Autowired
    public TraineeService(TraineeDao traineeDao, ProfileService profileService) {
        this.traineeDao = traineeDao;
        this.profileService = profileService;
    }

    public Trainee createTrainee(Trainee trainee) {
        Trainee traineeToSave = Trainee.builder()
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .userName(profileService.generateUsername(trainee.getFirstName(), trainee.getLastName()))
                .password(profileService.generatePassword())
                .isActive(trainee.getIsActive())
                .userId(trainee.getUserId())
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .build();
        traineeDao.save(traineeToSave);
        logger.info("Created trainee: {}", traineeToSave.getUserId());
        return traineeToSave;
    }

    public Trainee updateTrainee(Trainee trainee) {
        traineeDao.update(trainee);
        logger.info("Updated trainee: {}", trainee.getUserId());
        return trainee;
    }

    public Trainee getTrainee(Integer id) {
        Trainee trainee = traineeDao.get(id);
        logger.info("Retrieved trainee: {}", id);
        return trainee;
    }

    public List<Trainee> getAllTrainees() {
        List<Trainee> trainees = traineeDao.getAll();
        logger.info("Retrieved all trainees");
        return trainees;
    }

    public void deleteTrainee(Integer id) {
        traineeDao.delete(id);
        logger.info("Deleted trainee: {}", id);
    }
}
