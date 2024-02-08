package com.example.springcore.storage;


import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.service.ProfileService;
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
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainerStorage {
    private final ResourceLoader resourceLoader;
    private final ProfileService profileService;

    @Getter
    private final Map<Integer, Trainer> trainerStorageMap = new HashMap<>();
    @Value("${data.trainer.path}")
    private String dataFilePath;

    @Autowired
    public TrainerStorage(ResourceLoader resourceLoader, ProfileService profileService) {
        this.resourceLoader = resourceLoader;
        this.profileService = profileService;
    }

    @PostConstruct
    public void init() {
        Resource resource = resourceLoader.getResource(dataFilePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Integer userId = Integer.parseInt(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                Boolean isActive = Boolean.parseBoolean(parts[3]);
                TrainingType specialization = new TrainingType(parts[4]);
                String userName = profileService.generateUsername(firstName, lastName);
                String password = profileService.generatePassword();
                Trainer trainer = new Trainer(firstName, lastName, userName, password, isActive, userId, specialization);
                trainerStorageMap.put(userId, trainer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
