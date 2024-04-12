package com.example.springcore.service;

import com.example.springcore.dto.TrainingDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    void createTraining(TrainingDTO trainingDTO);

    List<TrainingDTO> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUserName);

    List<TrainingDTO> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingTypeName);
}
