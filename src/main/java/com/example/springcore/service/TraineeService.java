package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainerListToUpdateRequestDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;

import java.util.List;

public interface TraineeService {
    UserCredentialsDTO createTrainee(TraineeDTO traineeDTO);

    Trainee updateTrainee(TraineeDTO traineeDTO);

    void deleteTrainee(String username);

    Trainee getTraineeByUsername(String username);

    void linkTraineeAndTrainee(Trainee trainee, Trainer trainer);

    List<Trainer> updateTrainersListInTraineeByUsername(TraineeWithTrainerListToUpdateRequestDTO traineeWithTrainerListToUpdateRequestDTO);

    List<Trainer> getTrainersNotAssignedToTrainee(String username);
}
