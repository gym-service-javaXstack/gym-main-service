package com.example.springcore.mapper;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @Mapping(source = "trainee.user.userName", target = "traineeUserName")
    @Mapping(source = "trainer.user.userName", target = "trainerUserName")
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    TrainingDTO fromTrainingToTraineeTrainingDTO(Training training);

    @Mapping(source = "traineeUserName", target = "trainee.user.userName")
    @Mapping(source = "trainerUserName", target = "trainer.user.userName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    Training fromTrainingDTOtoTraining(TrainingDTO trainingDTO);

    @Mapping(source = "trainee.user.userName", target = "traineeUserName")
    @Mapping(source = "trainer.user.userName", target = "trainerUserName")
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    List<TrainingDTO> fromTrainingListToTraineeTrainingListDTO(List<Training> trainingList);
}
