package com.example.springcore.service;

import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.mapper.TrainingTypeMapper;
import com.example.springcore.model.TrainingType;
import com.example.springcore.repository.TrainingTypeDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingTypeService {
    private final TrainingTypeDao trainingTypeDao;
    private final TrainingTypeMapper trainingTypeMapper;

    @Transactional(readOnly = true)
    public TrainingType findTrainingTypeByName(String name) {
        log.info("Enter TrainingTypeService findTrainingTypeByName: {}", name);

        TrainingType trainingTypeByName = trainingTypeDao.findTrainingTypeByName(name);
        log.info("Exit TrainingTypeService findTrainingTypeByName: {}", name);
        return trainingTypeByName;

    }

    @Transactional(readOnly = true)
    public List<TrainingTypeDTO> getTrainingTypeList() {
        log.info("Entry TrainingTypeService getTrainingTypeList");

        List<TrainingTypeDTO> trainingTypeDTOS = trainingTypeMapper.fromTrainingTypeListToTrainingTypeDTOList(trainingTypeDao.getAllTrainingTypes());
        log.info("Exit TrainingTypeService getTrainingTypeList");
        return trainingTypeDTOS;
    }
}