package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import io.cucumber.java.en.Then;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GeneralStepsGlue {

    @Autowired
    private CucumberComponentContext context;

    //Errors
    @Then("an EntityNotFoundException is thrown")
    public void an_EntityNotFoundException_is_thrown() {
        assertNotNull(context.getException());
        assertEquals(EntityNotFoundException.class, context.getException().getClass());
    }

    @Then("an IllegalArgumentException is thrown")
    public void an_IllegalArgumentException_is_thrown() {
        assertNotNull(context.getException());
        assertEquals(IllegalArgumentException.class, context.getException().getClass());
    }

    // Validation
    @Then("the response should contain the username {string}")
    public void the_response_should_contain_the_username(String username) {
        assertEquals(context.getUserCredentialsDTO().getUsername(), username);
    }

    @Then("the password in the response has a length of {int}")
    public void the_password_in_the_response_has_a_length_of(Integer passwordLength) {
        assertEquals(passwordLength, context.getUserCredentialsDTO().getPassword().length());
    }
}
