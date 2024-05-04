package com.example.springcore.jms;

import com.example.springcore.dto.TrainerWorkLoadRequest;
import com.example.springcore.service.GymReportsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "activemq")
@RequiredArgsConstructor
@Slf4j
public class JmsClient implements GymReportsService {

    private final JmsTemplate jmsTemplate;

    @Override
    @CircuitBreaker(name = "gym-report")
    public ResponseEntity<Void> processTrainerWorkload(TrainerWorkLoadRequest request) {
        log.info("Entry JmsClient processTrainerWorkload");
        jmsTemplate.convertAndSend("trainerWorkloadQueue", request, message -> {
            message.setStringProperty("_type", "TrainerWorkLoadRequest");
            message.setStringProperty("X_Trace_Id", MDC.get("correlationId"));
            return message;
        });
        log.info("Exit JmsClient processTrainerWorkload");
        return ResponseEntity.ok().build();
    }

    @Override
    @CircuitBreaker(name = "gym-report")
    public Integer getTrainerSummaryByUsername(String username, int year, int monthValue, String authHeader) {
        log.info("Entry JmsClient getTrainerSummaryByUsername");
        jmsTemplate.send("trainerSummaryRequestQueue", session -> {
            Message message = session.createMessage();
            message.setStringProperty("username", username);
            message.setIntProperty("year", year);
            message.setIntProperty("monthValue", monthValue);
            message.setStringProperty("authHeader", authHeader);

            message.setStringProperty("X_Trace_Id", MDC.get("correlationId"));
            message.setJMSCorrelationID(MDC.get("correlationId"));
            return message;
        });


        Integer response = (Integer) jmsTemplate
                .receiveSelectedAndConvert(
                        "trainerSummaryResponseQueue",
                        String.format("JMSCorrelationID = '%s'", MDC.get("correlationId"))
                );

        log.info("Exit JmsClient getTrainerSummaryByUsername");
        return response != null
                ? response
                : 100000;
    }
}
