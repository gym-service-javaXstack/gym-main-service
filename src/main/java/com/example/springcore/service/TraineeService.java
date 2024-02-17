package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.impl.TraineeDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);
    private final TraineeDao traineeDao;
    private final ProfileService profileService;

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
        traineeDao.update(trainee, trainee.getUserId());
        logger.info("Updated trainee: {}", trainee.getUserId());
        return trainee;
    }

    public Optional<Trainee> getTrainee(Integer id) {
        Optional<Trainee> trainee = traineeDao.get(id);
        trainee.ifPresent(t -> logger.info("Retrieved trainee: {}", id));
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
