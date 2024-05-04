package com.example.springcore.config;

import com.example.springcore.handler.interceptor.FeignHeaderInterceptor;
import com.example.springcore.util.RetreiveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.springcore.feign")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@ConditionalOnProperty(name = "INTERACTION_BETWEEN_MICROSERVICES", havingValue = "feign", matchIfMissing = true)
public class FeignConfig {

    @Bean
    public FeignHeaderInterceptor customHeaderInterceptor() {
        return new FeignHeaderInterceptor();
    }

    @Bean
    public ErrorDecoder customFeignErrorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }
}
