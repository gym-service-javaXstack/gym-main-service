package com.example.springcore.controller;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/training")
public interface TrainingApi {

    @PostMapping
    ResponseEntity<Void> createTraining(@Valid @RequestBody TrainingDTO trainingDTO);

    @GetMapping("/type")
    ResponseEntity<List<TrainingTypeDTO>> getTrainingTypeList();
}
