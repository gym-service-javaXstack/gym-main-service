package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import com.example.springcore.service.TrainingTypeService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainingTypeServiceGlue {

    public static final String TRAINING_TYPE_NAME = "Test";

    @Autowired
    private CucumberComponentContext context;

    @Autowired
    TrainingTypeService trainingTypeService;

    @When("a request to fetch all training types is made")
    public void a_request_to_fetch_all_training_types_is_made() {
        context.setTrainingTypeList(trainingTypeService.getTrainingTypeList());
    }

    @Then("a list of all training types is returned")
    public void a_list_of_all_training_types_is_returned() {
        assertNotNull(context.getTrainingTypeList());
        assertFalse(context.getTrainingTypeList().isEmpty());
    }

    @When("a request to fetch training type by valid name {string} is made")
    public void a_request_to_fetch_training_type_by_name_is_made(String name) {
        context.setTrainingType(trainingTypeService.findTrainingTypeByName(name));
    }

    @Then("a training type of required name is returned")
    public void a_training_type_of_required_name_is_returned() {
        assertNotNull(context.getTrainingType());
        assertEquals(TRAINING_TYPE_NAME, context.getTrainingType().getTrainingTypeName());
    }

    @When("a request to fetch training type by invalid name {string} is made")
    public void a_request_to_fetch_training_type_by_invalid_name(String name) {
        try {
            context.setTrainingType(trainingTypeService.findTrainingTypeByName(name));
        } catch (Exception e) {
            context.setException(e);
        }
    }
}
