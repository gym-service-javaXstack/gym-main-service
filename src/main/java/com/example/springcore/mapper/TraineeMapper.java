package com.example.springcore.mapper;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TraineeMapper {
    Trainee fromTraineeDTOToTrainee(TraineeDTO traineeDTO);

    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    TraineeDTO fromTraineeToTraineeDTO(Trainee trainee);

}