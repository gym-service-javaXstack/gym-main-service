package com.example.springcore.config;

import com.example.springcore.handler.interceptor.FeignHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignHeaderInterceptor customHeaderInterceptor() {
        return new FeignHeaderInterceptor();
    }
}
