package com.example.springcore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Slf4j
@Component
public class TransactionLoggingAspect {

    private static final String TRANSACTION_ID_KEY = "transactionId";

    public static String getTransactionId() {
        return MDC.get(TRANSACTION_ID_KEY);
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        MDC.put(TRANSACTION_ID_KEY, UUID.randomUUID().toString());
        log.info("Starting transaction [{}] for method {}", getTransactionId(), joinPoint.getSignature().getName());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error in transaction [{}]: {}", getTransactionId(), throwable.getMessage());
            throw throwable;
        } finally {
            log.info("Finished transaction [{}] for method {}", getTransactionId(), joinPoint.getSignature().getName());
            MDC.remove(TRANSACTION_ID_KEY);
        }

        return result;
    }
}
