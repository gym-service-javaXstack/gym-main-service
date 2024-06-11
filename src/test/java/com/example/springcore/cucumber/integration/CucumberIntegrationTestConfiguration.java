package com.example.springcore.cucumber.integration;

import com.example.springcore.SpringCoreApplication;
import com.example.springcore.cucumber.config.CucumberSpringConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringCoreApplication.class)
@AutoConfigureMockMvc
public class CucumberIntegrationTestConfiguration extends CucumberSpringConfiguration {
}
