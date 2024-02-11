package com.example.springcore.storage.impl;

import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.storage.AbstractStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrainerStorage extends AbstractStorage<Trainer> {
    @Value("${data.trainer.path}")
    private String dataFilePath;

    @Override
    protected String getDataFilePath() {
        return dataFilePath;
    }

    @Override
    protected Trainer parseLine(String line) {
        String[] parts = line.split(",");
        Integer userId = Integer.parseInt(parts[0]);
        String firstName = parts[1];
        String lastName = parts[2];
        Boolean isActive = Boolean.parseBoolean(parts[3]);
        TrainingType specialization = new TrainingType(parts[4]);
        String userName = profileService.generateUsername(firstName, lastName);
        String password = profileService.generatePassword();
        return new Trainer(firstName, lastName, userName, password, isActive, userId, specialization);
    }

    @Override
    protected Integer getId(Trainer trainer) {
        return trainer.getUserId();
    }
}
