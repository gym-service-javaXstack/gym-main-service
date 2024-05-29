package com.example.springcore.cucumber.component;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Scope("cucumber-glue")
@Component
public class CucumberComponentContext {

    private final TrainerWithTraineesMapper trainerWithTraineesMapper;

    private UserCredentialsDTO userCredentialsDTO;
    private TrainerDTO trainerDTO;
    private TraineeDTO traineeDTO;
    private TrainingDTO trainingDTO;
    private TrainerWithTraineesDTO trainerWithTraineesDTO;

    private Trainer trainer;
    private Trainee trainee;
    private TrainingType trainingType;

    private String traineeUsername;
    private String trainerUsername;
    private String trainingTypeName;

    private LocalDate fromDate;
    private LocalDate toDate;
    private String traineeName;
    private String trainerName;

    private List<TrainingType> trainingTypeList;
    private List<String> usernames;
    private List<Trainer> trainers;

    private Exception exception;
}
