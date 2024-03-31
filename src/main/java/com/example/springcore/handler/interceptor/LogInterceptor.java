package com.example.springcore.handler.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            Map<String, String[]> paramMap = request.getParameterMap();
            String params = paramMap.entrySet().stream()
                    .map(entry -> entry.getKey() + "=[" + String.join(",", entry.getValue()) + "]")
                    .collect(Collectors.joining(", "));
            log.info(
                    "Request method: {}, Request URI: {}, Request parameters: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    params
            );
        }

        return true;
    }
}
