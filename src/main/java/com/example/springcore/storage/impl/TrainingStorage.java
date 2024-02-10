package com.example.springcore.storage.impl;

import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingStorage implements Storage {
    private ResourceLoader resourceLoader;

    @Getter
    private final Map<Integer, Training> trainingStorageMap = new HashMap<>();

    @Value("${data.training.path}")
    private String dataFilePath;

    @Autowired
    public void setInjection(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    @PostConstruct
    public void init() {
        Resource resource = resourceLoader.getResource(dataFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Integer trainingId = Integer.parseInt(parts[0]);
                Integer traineeId = Integer.parseInt(parts[1]);
                Integer trainerId = Integer.parseInt(parts[2]);
                String trainingName = parts[3];
                TrainingType trainingType = new TrainingType(parts[4]);
                LocalDate trainingDate = LocalDate.parse(parts[5]);
                Integer duration = Integer.parseInt(parts[6]);

                Training training = new Training(trainingId, traineeId, trainerId, trainingName, trainingType, trainingDate, duration);
                trainingStorageMap.put(trainingId, training);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
