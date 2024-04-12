package com.example.springcore.mapper;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TraineeTrainingMapper {

    @Mapping(target = "traineeUserName", ignore = true)
    @Mapping(source = "trainer.user.userName", target = "trainerUserName")
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    TrainingDTO fromTrainingToTraineeTrainingDTO(Training training);

    @Mapping(source = "trainee.user.userName", target = "traineeUserName")
    @Mapping(source = "trainer.user.userName", target = "trainerUserName")
    @Mapping(source = "trainingType.trainingTypeName", target = "trainingTypeName")
    @Mapping(source = "trainingDate", target = "trainingDate")
    @Mapping(source = "duration", target = "duration")
    List<TrainingDTO> fromTrainingListToTraineeTrainingListDTO(List<Training> trainingList);
}
