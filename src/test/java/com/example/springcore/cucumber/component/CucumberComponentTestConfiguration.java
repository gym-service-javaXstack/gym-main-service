package com.example.springcore.cucumber.component;

import com.example.springcore.SpringCoreApplication;
import com.example.springcore.cucumber.config.CucumberSpringConfiguration;
import com.example.springcore.repository.TrainingRepository;
import io.cucumber.java.ParameterType;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringCoreApplication.class)
public class CucumberComponentTestConfiguration extends CucumberSpringConfiguration {

    @SpyBean
    private TrainingRepository trainingRepository;

    @ParameterType("\\d{4}-\\d{2}-\\d{2}")
    public LocalDate localDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
