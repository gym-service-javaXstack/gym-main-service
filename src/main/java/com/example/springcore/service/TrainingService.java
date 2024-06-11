package com.example.springcore.service;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    void createTraining(TrainingDTO trainingDTO);

    List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUserName);

    List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingTypeName);

}
