package com.example.springcore.cucumber.integration.glue;

import com.example.springcore.cucumber.integration.CucumberIntegrationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class TrainerControllerGlue {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CucumberIntegrationContext context;

}
