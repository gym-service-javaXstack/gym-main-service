package com.example.springcore.storage.impl;

import com.example.springcore.model.Trainee;
import com.example.springcore.storage.AbstractStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TraineeStorage extends AbstractStorage<Trainee> {
    @Value("${data.trainee.path}")
    private String dataFilePath;

    @Override
    protected String getDataFilePath() {
        return dataFilePath;
    }

    @Override
    protected Trainee parseLine(String line) {
        String[] parts = line.split(",");
        Integer userId = Integer.parseInt(parts[0]);
        String firstName = parts[1];
        String lastName = parts[2];
        Boolean isActive = Boolean.parseBoolean(parts[3]);
        String address = parts[4];
        LocalDate localDate = LocalDate.parse(parts[5]);
        String userName = profileService.generateUsername(firstName, lastName);
        String password = profileService.generatePassword();
        return new Trainee(firstName, lastName, userName, password, isActive, userId, address, localDate);
    }

    @Override
    protected Integer getId(Trainee trainee) {
        return trainee.getUserId();
    }
}
