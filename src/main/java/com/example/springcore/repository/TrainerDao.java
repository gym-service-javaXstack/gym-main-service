package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import com.example.springcore.storage.impl.TrainerStorage;
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
        return storage.getStorageMap().get(id);
    }

    public Trainer save(Trainer trainer) {
        int newId = storage.getStorageMap().size() + 1;
        trainer.setUserId(newId);
        storage.getStorageMap().put(newId, trainer);
        return storage.getStorageMap().get(newId);
    }

    public List<Trainer> getAll() {
        return new ArrayList<>(storage.getStorageMap().values());
    }
}
