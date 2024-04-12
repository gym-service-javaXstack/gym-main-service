package com.example.springcore.mapper;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TrainerMapper.class})
public interface TraineeWithTrainersMapper {
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.isActive", target = "isActive")
    @Mapping(source = "trainers", target = "trainers")
    TraineeWithTrainersDTO fromTraineeToTraineeWithTrainersDTO(Trainee trainee);

    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "isActive", target = "user.isActive")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    void updateTraineeFromDTO(TraineeDTO dto, @MappingTarget Trainee entity);

}
