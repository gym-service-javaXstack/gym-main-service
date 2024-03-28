package com.example.springcore.mapper;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TrainerMapper {
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(target = "specialization.id",ignore = true)
    TrainerDTO fromTrainerToTrainerDTO(Trainer trainer);

    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(target = "specialization.id",ignore = true)
    List<TrainerDTO> fromTrainerListToTrainerListDTO(List<Trainer> trainerList);
}
