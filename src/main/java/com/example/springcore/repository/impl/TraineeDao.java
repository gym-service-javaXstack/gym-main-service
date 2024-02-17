package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.AbstractDao;
import com.example.springcore.storage.impl.TraineeStorage;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TraineeDao extends AbstractDao<Trainee, TraineeStorage> {

    public TraineeDao(TraineeStorage storage) {
        super(storage);
    }

    @Override
    protected void setId(Trainee trainee, Integer id) {
        trainee.setUserId(id);
    }

    @Override
    protected Map<Integer, Trainee> getStorageMap() {
        return storage.getStorageMap();
    }
}
