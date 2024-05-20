package com.example.springcore.cucumber.component.glue;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.UserService;
import com.example.springcore.service.util.TestUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceGlue {

    @Autowired
    private UserService userService;

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserCredentialsDTO trainee;

    @Given("there is a trainee with firstname {string} and lastname {string}")
    public void there_is_a_user_with_firstname_and_lastname(String firstname, String lastname) {
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();
        traineeDTO.setFirstName(firstname);
        traineeDTO.setLastName(lastname);
        trainee = traineeService.createTrainee(traineeDTO);
    }

    @When("the user status is changed to {string}")
    public void the_user_status_is_changed_to(String newStatus) {
        userService.changeUserStatus(trainee.getUsername(), Boolean.parseBoolean(newStatus));
    }

    @Then("the user's status should be {string}")
    public void the_user_s_status_should_be(String expectedStatus) {
        Trainee updatedUser = traineeService.getTraineeByUsername(trainee.getUsername());
        assertEquals(Boolean.parseBoolean(expectedStatus), updatedUser.getUser().getIsActive());
    }

    @When("the user password is changed to {string}")
    public void the_user_password_is_changed_to(String newPassword) {
        userService.changeUserPassword(trainee.getUsername(), trainee.getPassword(), newPassword);
    }

    @Then("the user's password should be {string}")
    public void the_user_s_password_should_be(String newPassword) {
        Trainee updatedUser = traineeService.getTraineeByUsername(trainee.getUsername());
        boolean matches = passwordEncoder.matches(newPassword, updatedUser.getUser().getPassword());
        assertTrue(matches);
    }
}
