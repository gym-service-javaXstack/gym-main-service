package com.example.springcore.config;

import com.example.springcore.interceptor.CorrelationIdLoggerInterceptor;
import com.example.springcore.interceptor.LogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {
    private final LogInterceptor logInterceptor;
    private final CorrelationIdLoggerInterceptor correlationIdLoggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationIdLoggerInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(logInterceptor).addPathPatterns("/api/**");
    }

    @Bean
    SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
        return new ObjectMapperProvider(springDocConfigProperties);
    }

    @Bean
    SpringDocUIConfiguration SpringDocUIConfiguration(Optional<SwaggerUiConfigProperties> optionalSwaggerUiConfigProperties) {
        return new SpringDocUIConfiguration(optionalSwaggerUiConfigProperties);
    }
}