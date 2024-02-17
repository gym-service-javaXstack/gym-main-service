package com.example.springcore.repository.impl;

import com.example.springcore.model.Training;
import com.example.springcore.repository.AbstractDao;
import com.example.springcore.storage.impl.TrainingStorage;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDao extends AbstractDao<Training, TrainingStorage> {

    public TrainingDao(TrainingStorage storage) {
        super(storage);
    }

    @Override
    protected void setId(Training training, Integer id) {
        training.setTrainingId(id);

    }

    @Override
    protected Map<Integer, Training> getStorageMap() {
        return storage.getStorageMap();
    }
}
