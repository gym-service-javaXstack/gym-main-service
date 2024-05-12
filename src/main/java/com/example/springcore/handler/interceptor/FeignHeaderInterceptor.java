package com.example.springcore.handler.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class FeignHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-Trace-Id", getCorrelationId());
    }

    private static String getCorrelationId() {
        String correlationIdWithPrefix = MDC.get("correlationId");
        return correlationIdWithPrefix.substring(correlationIdWithPrefix.indexOf('[') + 1, correlationIdWithPrefix.indexOf(']'));
    }
}
