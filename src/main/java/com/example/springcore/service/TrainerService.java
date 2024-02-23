package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.model.User;
import com.example.springcore.repository.impl.TrainerDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDao trainerDao;
    private final ProfileService profileService;

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
        trainerDao.save(trainerToSave);
        logger.info("Created trainer: {}", trainerToSave.getUser().getId());
        return trainerToSave;
    }

    public Trainer updateTrainer(Trainer trainer) {
        Trainer update = trainerDao.save(trainer);
        logger.info("Updated trainer: {}", trainer.getUser().getId());
        return update;
    }
}
