package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import com.example.springcore.dto.TraineeDTO;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceGlue {

    @Autowired
    private CucumberComponentContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Given("there is a trainee with firstname {string} and lastname {string}")
    public void there_is_a_user_with_firstname_and_lastname(String firstname, String lastname) {
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();
        traineeDTO.setFirstName(firstname);
        traineeDTO.setLastName(lastname);
        context.setUserCredentialsDTO(traineeService.createTrainee(traineeDTO));
    }

    @When("the user status is changed to {string}")
    public void the_user_status_is_changed_to(String newStatus) {
        userService.changeUserStatus(context.getUserCredentialsDTO().getUsername(), Boolean.parseBoolean(newStatus));
    }

    @Then("the user's status should be {string}")
    public void the_user_s_status_should_be(String expectedStatus) {
        Trainee updatedUser = traineeService.getTraineeByUsername(context.getUserCredentialsDTO().getUsername());
        assertEquals(Boolean.parseBoolean(expectedStatus), updatedUser.getUser().getIsActive());
    }

    @When("the user password is changed to {string}")
    public void the_user_password_is_changed_to(String newPassword) {
        userService.changeUserPassword(context.getUserCredentialsDTO().getUsername(), context.getUserCredentialsDTO().getPassword(), newPassword);
    }

    @Then("the user's password should be {string}")
    public void the_user_s_password_should_be(String newPassword) {
        Trainee updatedUser = traineeService.getTraineeByUsername(context.getUserCredentialsDTO().getUsername());
        boolean matches = passwordEncoder.matches(newPassword, updatedUser.getUser().getPassword());
        assertTrue(matches);
    }

    @When("a request to change password with incorrect oldPassword {string}")
    public void a_request_to_change_password_with_incorrect_oldPassword(String incorrectPassword) {
        try {
            userService.changeUserPassword(context.getUserCredentialsDTO().getUsername(), incorrectPassword, incorrectPassword);
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @Given("creating users with first name {string} and last name {string} 3 times")
    public void there_are_users_with_first_name_and_last_name(String firstname, String lastname) {
        TraineeDTO traineeDTO = TestUtil.createTraineeDTO();
        traineeDTO.setFirstName(firstname);
        traineeDTO.setLastName(lastname);
        context.setUserCredentialsDTO(traineeService.createTrainee(traineeDTO));
    }

    @When("a request to fetch usernames by first name {string} and last name {string} is made")
    public void a_request_to_fetch_usernames_by_first_name_and_last_name(String firstName, String lastName) {
        context.setUsernames(userService.getUsernameByFirstNameAndLastName(firstName, lastName));
    }

    @Then("a list of usernames with the format {string} and {string} is returned")
    public void a_list_of_usernames_with_the_format_is_returned(String firstname, String lastname) {
        assertFalse(context.getUsernames().isEmpty());
        for (String username : context.getUsernames()) {
            assertTrue(username.startsWith(firstname + "." + lastname));
        }
    }
}
