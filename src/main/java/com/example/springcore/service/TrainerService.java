package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainer;

import java.util.List;

public interface TrainerService {
    UserCredentialsDTO createTrainer(TrainerDTO trainerDTO);

    Trainer updateTrainer(TrainerDTO trainerDTO);

    Trainer getTrainerByUserName(String userName);

    List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames);

    Integer getTrainerSummaryByUsername(
            String username,
            int year,
            int monthValue,
            String authHeader
    );
}
