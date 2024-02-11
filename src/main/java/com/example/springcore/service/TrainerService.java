package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.TrainerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDao trainerDao;
    private final ProfileService profileService;

    @Autowired
    public TrainerService(TrainerDao trainerDao, ProfileService profileService) {
        this.trainerDao = trainerDao;
        this.profileService = profileService;
    }

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

    public Trainer getTrainer(Integer id) {
        Trainer trainer = trainerDao.get(id);
        logger.info("Retrieved trainer: {}", id);
        return trainer;
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDao.getAll();
        logger.info("Retrieved all trainers");
        return trainers;
    }
}
