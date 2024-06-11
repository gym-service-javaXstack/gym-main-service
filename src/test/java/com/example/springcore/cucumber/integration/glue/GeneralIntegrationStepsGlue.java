package com.example.springcore.cucumber.integration.glue;

import com.example.springcore.cucumber.integration.CucumberIntegrationContext;
import com.example.springcore.dto.AuthenticationResponseDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GeneralIntegrationStepsGlue {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CucumberIntegrationContext context;

    @Autowired
    private JmsTemplate jmsTemplate;

    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("the user with credentials as below:")
    public void the_user_with_credentials_as_below(Map<String, String> map) {
        if (scenario.getSourceTagNames().contains("@skipAuthentication")) {
            return;
        }

        String username = map.get("username");
        String password = map.get("password");

        UserCredentialsDTO userCredentialsDto = new UserCredentialsDTO();
        userCredentialsDto.setUsername(username);
        userCredentialsDto.setPassword(password);

        context.setBody(userCredentialsDto);
    }

    @When("the user calls end point in order to authenticate")
    public void the_user_calls_end_point_in_order_to_authenticate() throws Exception {
        if (scenario.getSourceTagNames().contains("@skipAuthentication")) {
            return;
        }

        ResultActions result = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(context.getBody()));
        result.andDo(print());

        context.setResult(result);
    }

    @Then("the authentication response returned with status code as {int}")
    public void the_authentication_response_returned_with_status_code_as(Integer statusCode) throws Exception {
        if (scenario.getSourceTagNames().contains("@skipAuthentication")) {
            return;
        }

        ResultActions result = context.getResult();
        result.andExpect(status().is(statusCode));
        String response = result.andReturn().getResponse().getContentAsString();

        AuthenticationResponseDTO authenticationResponseDTO = objectMapper.readValue(response, AuthenticationResponseDTO.class);
        context.setToken(authenticationResponseDTO.getJwt());
    }

    @When("the user calls authorized end point {string} with method as 'GET'")
    public void the_user_calls_authorized_end_point_with_method_as_get(String endPoint) throws Exception {
        ResultActions result = mockMvc.perform(get(endPoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + context.getToken()));
        result.andDo(print());

        context.setResult(result);
    }

    @Then("the response returned with status code as {int}")
    public void the_response_returned_with_status_code_as(Integer statusCode) throws Exception {
        context.getResult().andExpect(status().is(statusCode));
    }

    @Then("the response data should include the first name {string} and last name {string}")
    public void the_response_data_should_include_the_first_name_and_last_name(String firstName, String lastName) throws Exception {
        context.getResult().andExpectAll(
                jsonPath("$.firstName").value(firstName),
                jsonPath("$.lastName").value(lastName)
        );
    }
}
