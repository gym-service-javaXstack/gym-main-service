package com.example.springcore.interceptor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@Aspect
@Slf4j
@Component
public class TransactionLoggingAspect {

    @PersistenceContext
    private EntityManager entityManager;

    private ThreadLocal<String> transactionId = new ThreadLocal<>();

    private ThreadLocal<Integer> transactionCount = ThreadLocal.withInitial(() -> 0);

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        if (transactionId.get() == null) {
            Session session = entityManager.unwrap(Session.class);
            session.doWork(connection -> {
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT pg_backend_pid()")) {
                    if (resultSet.next()) {
                        transactionId.set(String.valueOf(resultSet.getInt(1)));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            MDC.put("txId", String.format("Persistence Context Id: [%s], DB Transaction Id: [%s]", UUID.randomUUID(), transactionId.get()));
        }

        transactionCount.set(transactionCount.get() + 1);

        try {
            return joinPoint.proceed();
        } finally {
            transactionCount.set(transactionCount.get() - 1);

            if (transactionCount.get() == 0) {
                MDC.remove("txId");
                transactionId.remove();
            }
        }
    }
}
