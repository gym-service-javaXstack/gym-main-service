package com.example.springcore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ControllerLoggingAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logControllerCall(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Calling controller method: {}, args: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error in controller method {}: {}", joinPoint.getSignature().getName(), throwable.getMessage());
            throw throwable;
        }

        log.info("Controller method {} returned: {}", joinPoint.getSignature().getName(), result);
        return result;
    }
}
