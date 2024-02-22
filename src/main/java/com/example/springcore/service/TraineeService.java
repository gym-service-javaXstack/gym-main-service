package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.User;
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
        User user = User.builder()
                .firstName(trainee.getUser().getFirstName())
                .lastName(trainee.getUser().getLastName())
                .userName(profileService.generateUsername(trainee.getUser().getFirstName(), trainee.getUser().getLastName()))
                .password(profileService.generatePassword())
                .isActive(trainee.getUser().getIsActive())
                .build();

        Trainee traineeToSave = Trainee.builder()
                .user(user)
                .address(trainee.getAddress())
                .dateOfBirth(trainee.getDateOfBirth())
                .build();

        traineeDao.save(traineeToSave);
        logger.info("Created trainee: {}", traineeToSave.getUser().getId());
        return traineeToSave;
    }

    public Trainee updateTrainee(Trainee trainee) {
        traineeDao.save(trainee);
        logger.info("Updated trainee: {}", trainee.getUser().getId());
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
