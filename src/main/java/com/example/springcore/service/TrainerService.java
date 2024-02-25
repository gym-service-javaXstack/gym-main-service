package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.User;
import com.example.springcore.repository.impl.TrainerDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerDao trainerDao;
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;

    @Transactional
    public Trainer createTrainer(Trainer trainer) {
        User user = User.builder()
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .userName(profileService.generateUsername(trainer.getUser().getFirstName(), trainer.getUser().getLastName()))
                .password(profileService.generatePassword())
                .isActive(trainer.getUser().getIsActive())
                .build();

        Trainer trainerToSave = Trainer.builder()
                .user(user)
                .specialization(trainer.getSpecialization())
                .build();

        Trainer saved = trainerDao.save(trainerToSave);
        log.info("Created trainer: {}", trainerToSave.getUser().getId());
        return saved;
    }

    @Transactional
    public Trainer updateTrainer(Trainer trainer) {
        if (!authenticationService.isAuthenticated(trainer.getUser().getUserName())) {
            throw new RuntimeException("User is not authenticated");
        }
        Trainer updated = trainerDao.update(trainer);
        log.info("Updated trainer: {}", trainer.getUser().getId());
        return updated;
    }

    @Transactional
    public Trainer changeTrainerPassword(String username, String newPassword) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        Trainer trainerWithNewPassword = trainerDao.changePassword(username, newPassword, User::getTrainer);
        log.info("Updated trainer password: {}", trainerWithNewPassword.getUser().getUserName());
        return trainerWithNewPassword;
    }

    @Transactional(readOnly = true)
    public Optional<Trainer> getByUsername(String username) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        Optional<Trainer> byUsername = trainerDao.getUserByUsername(username, User::getTrainer);
        log.info("getByUsername trainer: {}", username);
        return byUsername;
    }

    @Transactional
    public void changeTrainerStatus(String username, boolean isActive) {
        if (!authenticationService.isAuthenticated(username)) {
            throw new RuntimeException("User is not authenticated");
        }
        trainerDao.changeUserStatus(username, isActive);
    }
}
