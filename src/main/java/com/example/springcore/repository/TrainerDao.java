package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import com.example.springcore.storage.TrainerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainerDao {
    private final TrainerStorage storage;
    @Autowired
    public TrainerDao(TrainerStorage storage) {
        this.storage = storage;
    }

    public Trainer get(Integer id) {
        return storage.getTrainerStorageMap().get(id);
    }

    public void save(Trainer trainer) {
        storage.getTrainerStorageMap().put(trainer.getUserId(), trainer);
    }

    public List<Trainer> getAll() {
        return new ArrayList<>(storage.getTrainerStorageMap().values());
    }

    public void delete(Integer id) {
        storage.getTrainerStorageMap().remove(id);
    }
}
