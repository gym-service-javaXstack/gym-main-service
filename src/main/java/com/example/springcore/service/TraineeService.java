package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeDao traineeDao;
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

    @Transactional
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

        Trainee saved = traineeDao.save(traineeToSave);
        log.info("Created trainee: {}", saved.getUser().getId());
        return saved;
    }

    @Transactional
    public Trainee updateTrainee(Trainee trainee) {
        authenticationService.isAuthenticated(trainee.getUser().getUserName());
        Trainee updated = traineeDao.update(trainee);
        log.info("Updated trainee: {}", updated.getUser().getId());
        return updated;
    }

    @Transactional
    public void deleteTrainee(String username) {
        authenticationService.isAuthenticated(username);
        traineeDao.delete(username);
        log.info("Deleted trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> getTraineeByUsername(String username) {
        authenticationService.isAuthenticated(username);
        Optional<Trainee> byUsername = traineeDao.getTraineeByUsername(username);
        log.info("getByUsername trainee: {}", username);
        return byUsername;
    }

    @Transactional
    public void updateTraineesTrainersList(Trainee trainee, Trainer trainer) {
        traineeDao.updateTraineesTrainersList(trainee, trainer);
        log.info("Updating trainee's trainers list: ");
    }

    @Transactional(readOnly = true)
    public List<Trainer> getTrainersNotAssignedToTrainee(String username) {
        authenticationService.isAuthenticated(username);
        List<Trainer> trainersNotAssignedToTrainee = traineeDao.getTrainersNotAssignedToTrainee(username);
        log.info("getTrainersNotAssignedToTrainee method: {}", username);
        return trainersNotAssignedToTrainee;
    }

    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, TrainingType trainingType) {
        authenticationService.isAuthenticated(username);
        List<Training> traineeTrainingsByCriteria = traineeDao.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
        log.info("getTraineeTrainingsByCriteria method: {}", username);
        return traineeTrainingsByCriteria;
    }
}
