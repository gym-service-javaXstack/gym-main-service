package com.example.springcore.service;

import com.example.springcore.model.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    TrainingType findTrainingTypeByName(String name);

    List<TrainingType> getTrainingTypeList();
}
