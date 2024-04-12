package com.example.springcore.mapper;

import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.model.TrainingType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    List<TrainingTypeDTO> fromTrainingTypeListToTrainingTypeDTOList(List<TrainingType> trainingTypeList);
}
