package com.example.springcore;

import com.example.springcore.config.FeignConfig;
import com.example.springcore.config.JmsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({FeignConfig.class, JmsConfig.class})
public class SpringCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCoreApplication.class, args);
    }
}