package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerDao trainerDao;

    @Autowired
    public TrainerService(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    public Trainer createTrainer(Trainer trainer) {
        trainerDao.save(trainer);
        return trainer;
    }

    public Trainer updateTrainer(Trainer trainer) {
        trainerDao.save(trainer);
        return trainer;
    }

    public Trainer getTrainer(Integer id) {
        return trainerDao.get(id);
    }

    public List<Trainer> getAllTrainers(){
        return trainerDao.getAll();
    }
}
