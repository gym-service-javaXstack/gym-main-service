package com.example.springcore.mapper;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainerTrainingMapper {

    @Mapping(source = "trainee.user.username",target = "traineeUserName")
    @Mapping(target = "trainerUserName", ignore = true)
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    TrainingDTO fromTrainingToTrainerTrainingDTO(Training training);

    @Mapping(source = "trainee.user.username", target = "traineeUserName")
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    List<TrainingDTO> fromTrainingListToTrainerTrainingListDTO(List<Training> trainingList);
}
