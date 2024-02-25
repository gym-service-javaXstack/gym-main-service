package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.User;
import com.example.springcore.repository.impl.TraineeDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("Created trainee: {}", traineeToSave.getUser().getId());
        return saved;
    }

    @Transactional
    public Trainee updateTrainee(Trainee trainee) {
        if (!authenticationService.isAuthenticated(trainee.getUser().getUserName())) {
            throw new RuntimeException("User is not authenticated");
        }
            Trainee updated = traineeDao.update(trainee);
        log.info("Updated trainee: {}", trainee.getUser().getId());
        return updated;
    }

    @Transactional
    public Trainee changeTraineePassword(String username, String newPassword) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        Trainee traineeWithNewPassword = traineeDao.changePassword(username, newPassword, User::getTrainee);
        log.info("Updated trainee password: {}", traineeWithNewPassword.getUser().getUserName());
        return traineeWithNewPassword;
    }

    @Transactional
    public void deleteTrainee(String username) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated " + username);
        }
        traineeDao.delete(username);
        log.info("Deleted trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> getByUsername(String username) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        Optional<Trainee> byUsername = traineeDao.getUserByUsername(username, User::getTrainee);
        log.info("getByUsername trainee: {}", username);
        return byUsername;
    }

    @Transactional
    public void changeTraineeStatus(String username, boolean isActive) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        traineeDao.changeUserStatus(username, isActive);
    }
}
