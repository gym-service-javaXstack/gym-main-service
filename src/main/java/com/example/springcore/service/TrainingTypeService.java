package com.example.springcore.service;

import com.example.springcore.model.TrainingType;
import com.example.springcore.repository.TrainingTypeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeDao trainingTypeDao;

    @Transactional(readOnly = true)
    public TrainingType findTrainingTypeByName(String name) {
        return trainingTypeDao.findTrainingTypeByName(name);
    }
}
