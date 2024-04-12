package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainerListToUpdateRequestDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;

import java.util.List;

public interface TraineeService {
    UserCredentialsDTO createTrainee(TraineeDTO traineeDTO);

    TraineeWithTrainersDTO updateTrainee(TraineeDTO traineeDTO);

    void deleteTrainee(String username);

    TraineeWithTrainersDTO getTraineeDTOByUsername(String username);

    Trainee getTraineeByUsername(String username);

    void linkTraineeAndTrainee(Trainee trainee, Trainer trainer);

    List<TrainerDTO> updateTrainersListInTraineeByUsername(TraineeWithTrainerListToUpdateRequestDTO traineeWithTrainerListToUpdateRequestDTO);

    List<TrainerDTO> getTrainersNotAssignedToTrainee(String username);
}
