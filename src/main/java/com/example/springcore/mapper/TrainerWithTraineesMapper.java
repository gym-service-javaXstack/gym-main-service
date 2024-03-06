package com.example.springcore.mapper;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {UserMapper.class, TraineeMapper.class})
public interface TrainerWithTraineesMapper {
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.isActive", target = "isActive")
    @Mapping(source = "trainees", target = "trainees")
    TrainerWithTraineesDTO fromTrainerToTrainerWithTraineesDTO(Trainer trainer);

    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "isActive", target = "user.isActive")
    @Mapping(target = "specialization.trainingTypeName", ignore = true)
    void updateTrainerFromDTO(TrainerDTO dto, @MappingTarget Trainer entity);
}