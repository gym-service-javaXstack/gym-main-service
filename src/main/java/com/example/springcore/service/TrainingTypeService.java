package com.example.springcore.service;

import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.model.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType findTrainingTypeByName(String name);

    List<TrainingTypeDTO> getTrainingTypeList();
}
