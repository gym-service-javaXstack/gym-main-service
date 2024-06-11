package com.example.springcore.controller.impl;

import com.example.springcore.controller.TrainingApi;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.mapper.TrainingTypeMapper;
import com.example.springcore.service.TrainingService;
import com.example.springcore.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TrainingControllerImpl implements TrainingApi {
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public ResponseEntity<Void> createTraining(TrainingDTO trainingDTO) {
        trainingService.createTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TrainingTypeDTO>> getTrainingTypeList() {
        List<TrainingTypeDTO> trainingTypeList = trainingTypeMapper.fromTrainingTypeListToTrainingTypeDTOList(trainingTypeService.getTrainingTypeList());
        return new ResponseEntity<>(trainingTypeList, HttpStatus.OK);
    }
}
