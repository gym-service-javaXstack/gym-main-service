package com.example.springcore.storage.impl;

import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.storage.AbstractStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TrainingStorage extends AbstractStorage<Training> {
    @Value("${data.training.path}")
    private String dataFilePath;

    @Override
    protected String getDataFilePath() {
        return dataFilePath;
    }

    @Override
    protected Training parseLine(String line) {
        String[] parts = line.split(",");
        Integer trainingId = Integer.parseInt(parts[0]);
        Integer traineeId = Integer.parseInt(parts[1]);
        Integer trainerId = Integer.parseInt(parts[2]);
        String trainingName = parts[3];
        TrainingType trainingType = new TrainingType(parts[4]);
        LocalDate trainingDate = LocalDate.parse(parts[5]);
        Integer duration = Integer.parseInt(parts[6]);
        return new Training(trainingId, traineeId, trainerId, trainingName, trainingType, trainingDate, duration);
    }

    @Override
    protected Integer getId(Training training) {
        return training.getTrainingId();
    }
}
