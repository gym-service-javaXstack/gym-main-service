package com.example.springcore.feign;

import com.example.springcore.config.FeignConfig;
import com.example.springcore.dto.TrainerWorkLoadRequest;
import com.example.springcore.service.GymReportsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gym-report", configuration = FeignConfig.class)
@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "feign", matchIfMissing = true)
@Primary
public interface GymReportsFeignClient extends GymReportsService {

    @PostMapping("/api/v1/workload")
    @CircuitBreaker(name = "gym-report")
    @Override
    ResponseEntity<Void> processTrainerWorkload(
            @RequestBody TrainerWorkLoadRequest request
    );

    @GetMapping("/api/v1/workload")
    @CircuitBreaker(name = "gym-report")
    @Override
    Integer getTrainerSummaryByUsername(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") @Min(1) @Max(12) int monthValue,
            @NotNull @RequestHeader("Authorization") String authHeader
    );
}
