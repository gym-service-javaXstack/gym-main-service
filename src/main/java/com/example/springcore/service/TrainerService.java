package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerDao trainerDao;
    private final ProfileService profileService;

    @Autowired
    public TrainerService(TrainerDao trainerDao, ProfileService profileService) {
        this.trainerDao = trainerDao;
        this.profileService = profileService;
    }

    public Trainer createTrainer(Trainer trainer) {
        Trainer trainerToSave = new Trainer(
                trainer.getFirstName(),
                trainer.getLastName(),
                profileService.generateUsername(trainer.getFirstName(), trainer.getLastName()),
                profileService.generatePassword(),
                trainer.getIsActive(),
                trainer.getUserId(),
                trainer.getSpecialization());
        trainerDao.save(trainerToSave);
        return trainerToSave;
    }

    public Trainer getTrainer(Integer id) {
        return trainerDao.get(id);
    }

    public List<Trainer> getAllTrainers() {
        return trainerDao.getAll();
    }
}
