package com.example.springcore.service;

import com.example.springcore.dto.TrainerWorkLoadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface GymReportsService {

    ResponseEntity<Void> processTrainerWorkload(
            @RequestBody TrainerWorkLoadRequest request
    );

    Integer getTrainerSummaryByUsername(
            String username,
            int year,
            int monthValue,
            String authHeader
    );
}
