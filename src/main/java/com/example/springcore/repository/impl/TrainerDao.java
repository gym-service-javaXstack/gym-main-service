package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.AbstractDao;
import com.example.springcore.storage.impl.TrainerStorage;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainerDao extends AbstractDao<Trainer,TrainerStorage> {

    public TrainerDao(TrainerStorage storage) {
        super(storage);
    }

    @Override
    protected void setId(Trainer trainer, Integer id) {
        trainer.setUserId(id);
    }

    @Override
    protected Map<Integer, Trainer> getStorageMap() {
        return storage.getStorageMap();
    }
}
