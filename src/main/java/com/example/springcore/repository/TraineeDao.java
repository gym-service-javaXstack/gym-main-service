package com.example.springcore.repository;

import com.example.springcore.model.Trainee;
import com.example.springcore.storage.impl.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TraineeDao {
    private final TraineeStorage storage;

    @Autowired
    public TraineeDao(TraineeStorage storage) {
        this.storage = storage;
    }

    public Trainee get(Integer id) {
        return storage.getStorageMap().get(id);
    }

    public Trainee save(Trainee trainee) {
        int newId = storage.getStorageMap().size() + 1;
        trainee.setUserId(newId);
        storage.getStorageMap().put(newId, trainee);
        return storage.getStorageMap().get(newId);
    }

    public Trainee update(Trainee trainee) {
        storage.getStorageMap().put(trainee.getUserId(), trainee);
        return storage.getStorageMap().get(trainee.getUserId());
    }

    public List<Trainee> getAll() {
        return new ArrayList<>(storage.getStorageMap().values());
    }

    public void delete(Integer id) {
        storage.getStorageMap().remove(id);
    }
}
