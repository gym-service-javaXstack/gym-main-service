package com.example.springcore.service;

import com.example.springcore.model.Trainer;
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
        Trainer trainerToSave = Trainer.builder()
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .userName(profileService.generateUsername(trainer.getFirstName(), trainer.getLastName()))
                .password(profileService.generatePassword())
                .isActive(trainer.getIsActive())
                .userId(trainer.getUserId())
                .specialization(trainer.getSpecialization())
                .build();
        trainerDao.save(trainerToSave);
        logger.info("Created trainer: {}", trainerToSave.getUserId());
        return trainerToSave;
    }

    public Trainer updateTrainer(Trainer trainer) {
        Trainer update = trainerDao.update(trainer, trainer.getUserId());
        logger.info("Updated trainer: {}", trainer.getUserId());
        return update;
    }

    public Optional<Trainer> getTrainer(Integer id) {
        Optional<Trainer> trainer = trainerDao.get(id);
        trainer.ifPresent(t -> logger.info("Retrieved trainer: {}", id));
        return trainer;
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDao.getAll();
        logger.info("Retrieved all trainers");
        return trainers;
    }
}
