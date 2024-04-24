package com.example.springcore.feign;

import com.example.springcore.config.FeignConfig;
import com.example.springcore.dto.TrainerWorkLoadRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gym-report", configuration = FeignConfig.class)
@Component
public interface GymReportsClient {

    @PostMapping("/api/v1/workload")
    @CircuitBreaker(name = "gym-report")
    ResponseEntity<Void> processTrainerWorkload(
            @RequestBody TrainerWorkLoadRequest request
    );

    @PostMapping("/api/v1/workload/list")
    @CircuitBreaker(name = "gym-report")
    ResponseEntity<Void> processListTrainerWorkload(
            @RequestBody List<TrainerWorkLoadRequest> request
    );

    @GetMapping("/api/v1/workload")
    @CircuitBreaker(name = "gym-report")
    Integer getTrainerSummaryByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") @Min(1) @Max(12) int monthValue,
            @NotNull @RequestHeader("Authorization") String authHeader
    );
}
