package com.example.springcore.handler.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final HttpServletRequest httpServletRequest;

    public CustomRequestBodyAdviceAdapter(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return httpServletRequest.getRequestURI().startsWith("/api/");
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        if (!httpServletRequest.getParameterMap().isEmpty()) {
            String params = httpServletRequest.getParameterMap().entrySet().stream()
                    .map(entry -> entry.getKey() + "=[" + String.join(",", entry.getValue()) + "]")
                    .collect(Collectors.joining(", "));

            log.info(
                    "Request method: {}, Request URI: {}, Request parameters: {}, Request body: {}",
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    params,
                    body
            );
        } else {
            log.info(
                    "Request method: {}, Request URI: {}, Request body: {}",
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    body
            );
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
