package com.example.springcore.cucumber.component.glue;

import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.model.TrainingType;
import com.example.springcore.service.TrainingTypeService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainingTypeServiceGlue {

    public static final String TRAINING_TYPE_NAME = "Test";

    @Autowired
    private TrainingTypeService trainingTypeService;

    private List<TrainingTypeDTO> trainingTypes;
    private TrainingType trainingType;

    private Exception exception;

    @When("a request to fetch all training types is made")
    public void a_request_to_fetch_all_training_types_is_made() {
        trainingTypes = trainingTypeService.getTrainingTypeList();
    }

    @Then("a list of all training types is returned")
    public void a_list_of_all_training_types_is_returned() {
        assertNotNull(trainingTypes);
        assertFalse(trainingTypes.isEmpty());
    }

    @When("a request to fetch training type by valid name {string} is made")
    public void a_request_to_fetch_training_type_by_name_is_made(String name) {
        trainingType = trainingTypeService.findTrainingTypeByName(name);
    }

    @Then("a training type of required name is returned")
    public void a_training_type_of_required_name_is_returned() {
        assertNotNull(trainingType);
        assertEquals(TRAINING_TYPE_NAME, trainingType.getTrainingTypeName());
    }

    @When("a request to fetch training type by invalid name {string} is made")
    public void a_request_to_fetch_training_type_by_invalid_name(String name) {
        try {
            trainingType = trainingTypeService.findTrainingTypeByName(name);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("an EntityNotFoundException is thrown with message {string}")
    public void an_EntityNotFoundException_is_thrown_with_message(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(EntityNotFoundException.class, exception.getClass());
        assertEquals(expectedMessage, exception.getMessage());
    }
}
