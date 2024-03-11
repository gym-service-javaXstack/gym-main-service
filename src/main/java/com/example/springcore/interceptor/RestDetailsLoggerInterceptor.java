package com.example.springcore.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;

@Slf4j
@Component
public class RestDetailsLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Incoming request: method={}, uri={}, headers={}",
                request.getMethod(),
                request.getRequestURI(),
                Collections.list(request.getHeaderNames()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Outgoing response: status={}, headers={}",
                response.getStatus(),
                response.getHeaderNames());
    }
}
