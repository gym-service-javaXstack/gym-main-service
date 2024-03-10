package com.example.springcore.service;

import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.mapper.TrainingTypeMapper;
import com.example.springcore.model.TrainingType;
import com.example.springcore.repository.TrainingTypeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeDao trainingTypeDao;
    private final TrainingTypeMapper trainingTypeMapper;

    @Transactional(readOnly = true)
    public TrainingType findTrainingTypeByName(String name) {
        return trainingTypeDao.findTrainingTypeByName(name);
    }

    @Transactional(readOnly = true)
    public List<TrainingTypeDTO> getTrainingTypeList() {
        return trainingTypeMapper.fromTrainingTypeListToTrainingTypeDTOList(trainingTypeDao.getAllTrainingTypes());
    }
}
