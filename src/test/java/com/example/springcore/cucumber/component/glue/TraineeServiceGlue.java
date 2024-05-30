package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.service.TraineeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TraineeServiceGlue {

    @Autowired
    private CucumberComponentContext context;

    @Autowired
    private TraineeService traineeService;

    // 001 - Create Trainee
    @Given("the trainee data as: {string}, {string}, {string}, {string}")
    public void the_trainee_data_as(String firstName, String lastName, String address, String dateOfBirth) {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setFirstName(firstName);
        traineeDTO.setLastName(lastName);
        traineeDTO.setAddress(address);
        traineeDTO.setDateOfBirth(LocalDate.parse(dateOfBirth));
        context.setTraineeDTO(traineeDTO);
    }

    @When("a request to create the trainee is made")
    public void a_request_to_create_the_trainee_is_made() {
        context.setUserCredentialsDTO(traineeService.createTrainee(context.getTraineeDTO()));
    }

    // 002 - Update Trainee
    @Given("an existing trainee with username {string}")
    public void an_existing_trainee_with_username(String username) {
        context.setTraineeUsername(username);
    }

    @Given("the updated trainee data as: {string}, {string}, {string}, {string}, {string}")
    public void the_updated_trainee_data_as(String firstName, String lastName, String address, String dateOfBirth, String isActive) {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUserName(context.getTraineeUsername());
        traineeDTO.setFirstName(firstName);
        traineeDTO.setLastName(lastName);
        traineeDTO.setAddress(address);
        traineeDTO.setDateOfBirth(LocalDate.parse(dateOfBirth));
        traineeDTO.setIsActive(Boolean.valueOf(isActive));
        context.setTraineeDTO(traineeDTO);
    }

    @When("a request to update the trainee is made")
    public void a_request_to_update_the_trainee_is_made() {
        context.setTrainee(traineeService.updateTrainee(context.getTraineeDTO()));
    }

    @Then("the trainee with username {string} should have the updated data")
    public void the_trainee_with_username_should_have_the_updated_data(String username) {
        Trainee updatedTrainee = traineeService.getTraineeByUsername(username);
        TraineeDTO traineeDTO = context.getTraineeDTO();
        assertEquals(traineeDTO.getFirstName(), updatedTrainee.getUser().getFirstName());
        assertEquals(traineeDTO.getLastName(), updatedTrainee.getUser().getLastName());
        assertEquals(traineeDTO.getAddress(), updatedTrainee.getAddress());
        assertEquals(traineeDTO.getDateOfBirth(), updatedTrainee.getDateOfBirth());
    }

    // 003 - Test to get a trainee by username
    @When("a request to get the trainee is made")
    public void a_request_to_get_the_trainee_is_made() {
        context.setTrainee(traineeService.getTraineeByUsername(context.getTraineeUsername()));
    }

    @Then("the trainee with {string} should be returned")
    public void the_trainee_with_should_be_returned(String username) {
        Trainee trainee = context.getTrainee();
        assertNotNull(trainee);
        assertEquals(username, trainee.getUser().getUserName());
    }

    // 004 - Test to get a non-existent trainee
    @Given("a non-existent trainee with username {string}")
    public void a_non_existent_trainee_with_username(String username) {
        context.setTraineeUsername(username);
    }

    @When("a request to get the non-existent trainee is made")
    public void a_request_to_get_the_non_existent_trainee_is_made() {
        try {
            traineeService.getTraineeByUsername(context.getTraineeUsername());
        } catch (Exception e) {
            context.setException(e);
        }
    }

    // 005 - Get trainers not assigned to an existing trainee
    @When("a request to get trainers not assigned to the trainee is made")
    public void a_request_to_get_trainers_not_assigned_to_the_trainee_is_made() {
        context.setTrainers(traineeService.getTrainersNotAssignedToTrainee(context.getTraineeUsername()));
    }

    @Then("a list of trainers not assigned to {string} should be returned")
    public void a_list_of_trainers_not_assigned_to_should_be_returned(String username) {
        List<Trainer> trainers = context.getTrainers();
        assertNotNull(trainers);
        assertFalse(trainers.isEmpty());

        trainers.forEach(trainer -> {
            boolean hasTraineeWithUsername = trainer.getTrainees().stream()
                    .anyMatch(trainee -> username.equals(trainee.getUser().getUserName()));

            assertFalse("Trainer " + trainer.getUser().getUserName() + " has a trainee with username " + username,
                    hasTraineeWithUsername);
        });
    }

    // 006 - Get trainers not assigned to a non-existent trainee
    @When("a request to get trainers not assigned to the non-existent trainee is made")
    public void a_request_to_get_trainers_not_assigned_to_the_non_existent_trainee_is_made() {
        try {
            traineeService.getTrainersNotAssignedToTrainee(context.getTraineeUsername());
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @When("a request to delete the trainee with username {string} is made")
    public void a_request_to_delete_the_non_exist_trainee_is_made(String username) {
        try {
            traineeService.deleteTrainee(username);
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @Then("the trainee with username {string} should be deleted")
    public void the_trainee_with_username_should_be_deleted(String username) {
        try {
            traineeService.deleteTrainee(username);
        } catch (Exception e) {
            context.setException(e);
        }

        assertNotNull(context.getException());
        assertTrue(context.getException() instanceof EntityNotFoundException);
        assertEquals("Trainee with username " + username + " not found", context.getException().getMessage());
    }
}
