package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.storage.impl.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDao {
    private final TrainingStorage storage;

    @Autowired
    public TrainingDao(TrainingStorage storage) {
        this.storage = storage;
    }

    public Training createTraining(Training training) {
        int newId = storage.getTrainingStorageMap().size() + 1;
        training.setTrainingId(newId);

        storage.getTrainingStorageMap().put(newId, training);
        return training;
    }

    public Training getTraining(int trainingId) {
        return storage.getTrainingStorageMap().get(trainingId);
    }

    public List<Training> getAll() {
        return new ArrayList<>(storage.getTrainingStorageMap().values());
    }
}
