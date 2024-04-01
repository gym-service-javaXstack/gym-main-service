package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainer;

import java.util.List;

public interface TrainerService {
    UserCredentialsDTO createTrainer(TrainerDTO trainerDTO);

    TrainerWithTraineesDTO updateTrainer(TrainerDTO trainerDTO);

    TrainerWithTraineesDTO getTrainerDTOByUserName(String userName);

    Trainer getTrainerByUserName(String userName);

    List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames);
}
