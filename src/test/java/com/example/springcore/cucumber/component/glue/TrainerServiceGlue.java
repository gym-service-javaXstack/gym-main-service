package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingTypeDTO;
import com.example.springcore.model.Trainer;
import com.example.springcore.service.TrainerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.MockedStatic;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import java.time.Month;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class TrainerServiceGlue {

    @Autowired
    private CucumberComponentContext context;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value(value = "${application.messaging.queue.trainer-summary.response}")
    private String trainerSummaryResponseQueue;

    private Integer expectedTrainerSummary;
    private Integer actualTrainerSummary;


    //Create Trainer
    @Given("the trainer data as: {string}, {string}, {string}")
    public void the_trainer_data_as(String firstName, String lastName, String specialization) {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeName(specialization);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstName(firstName);
        trainerDTO.setLastName(lastName);
        trainerDTO.setSpecialization(trainingTypeDTO);
        context.setTrainerDTO(trainerDTO);
    }

    @When("a request to create the trainer is made")
    public void a_request_to_create_the_trainer_is_made() {
        context.setUserCredentialsDTO(trainerService.createTrainer(context.getTrainerDTO()));
    }

    @Given("a list of trainer usernames {string}, {string}, {string}")
    public void a_list_of_trainer_usernames(String username1, String username2, String username3) {
        context.setUsernames(Arrays.asList(username1, username2, username3));
    }

    @When("a request to fetch trainers by username list is made")
    public void a_request_to_fetch_trainers_by_username_list_is_made() {
        context.setTrainers(trainerService.getTrainersByUsernameList(context.getUsernames()));
    }

    @Then("a list of trainers with the given usernames is returned")
    public void a_list_of_trainers_with_the_given_usernames_is_returned() {
        assertNotNull(context.getTrainers());
        assertEquals(context.getTrainers().size(), context.getUsernames().size());
        for (Trainer trainer : context.getTrainers()) {
            assertNotNull(trainer);
            assertNotNull(trainer.getUser());
            assertTrue(context.getUsernames().contains(trainer.getUser().getUserName()));
        }
    }

    @Given("a trainer with username {string}")
    public void a_trainer_with_username(String username) {
        context.setTrainerUsername(username);
    }

    @Given("data to update the trainer: first name {string}, last name {string}, training type {string}")
    public void data_to_update_the_trainer_first_name_last_name_training_type_name(
            String firstNameToUpdate,
            String lastNameToUpdate,
            String trainingTypeNameToUpdate
    ) {

        Trainer trainerByUserName = trainerService.getTrainerByUserName(context.getTrainerUsername());
        trainerByUserName.getSpecialization().setTrainingTypeName(trainingTypeNameToUpdate);
        context.setTrainer(trainerByUserName);

        TrainerDTO trainerDTO = context.getTrainerWithTraineesMapper().fromTrainerToTrainerWithTraineesDTO(trainerByUserName);
        trainerDTO.setFirstName(firstNameToUpdate);
        trainerDTO.setLastName(lastNameToUpdate);

        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingTypeName(trainingTypeNameToUpdate);
        trainerDTO.setSpecialization(trainingTypeDTO);
        trainerDTO.setUserName(trainerByUserName.getUser().getUserName());

        context.setTrainerDTO(trainerDTO);
    }

    @When("a request to update the trainer data is made")
    public void a_request_to_update_the_trainer_data_is_made() {
        context.setTrainer(trainerService.updateTrainer(context.getTrainerDTO()));
    }

    @When("an attempt is made to update the trainer's data")
    public void an_attempt_is_made_to_update_the_trainer_s_data() {
        try {
            TrainerDTO trainerDTO = new TrainerDTO();
            trainerDTO.setUserName(context.getTrainerUsername());
            trainerService.updateTrainer(trainerDTO);
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @Then("the updated trainer data is returned")
    public void the_updated_trainer_data_is_returned() {
        assertNotNull(context.getTrainer());
        assertEquals(context.getTrainerDTO().getFirstName(), context.getTrainer().getUser().getFirstName());
        assertEquals(context.getTrainerDTO().getLastName(), context.getTrainer().getUser().getLastName());
        assertEquals(context.getTrainerDTO().getSpecialization().getTrainingTypeName(), context.getTrainer().getSpecialization().getTrainingTypeName());
    }

    @When("a request to fetch the trainer data is made")
    public void a_request_to_fetch_the_trainer_data_is_made() {
        context.setTrainer(
                trainerService.getTrainerByUserName(
                        context.getTrainerUsername()
                )
        );
    }

    @When("a request to fetch the non exist trainer data is made")
    public void a_request_to_fetch_the_non_exist_trainer_data_is_made() {
        try {
            context.setTrainer(
                    trainerService.getTrainerByUserName(
                            context.getTrainerUsername()
                    )
            );
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @Then("the expected trainer data is returned")
    public void the_expected_trainer_data_is_returned() {
        assertNotNull(context.getTrainer());
        assertEquals(context.getTrainer().getUser().getUserName(), context.getTrainerUsername());
    }

    @Given("a trainer with username {string} has {int} working hours for the month {string} in {int}")
    public void a_trainer_with_username_has_working_hours_for_the_month_in(String username, int workload, String month, int year) {
        context.setTrainer(trainerService.getTrainerByUserName(username));

        expectedTrainerSummary = workload;
    }

    @When("a request to retrieve workload by username {string} for {string} {int} is made")
    public void a_request_to_retrieve_workload_by_name_for(String username, String month, int year) {
        UUID correlationId = UUID.randomUUID();
        try (MockedStatic<UUID> uuidUtils = mockStatic(UUID.class)) {
            uuidUtils.when(UUID::randomUUID).thenReturn(correlationId);
            UUID uuid = UUID.randomUUID();

            MDC.put("correlationId", uuid.toString());
            sendWorkloadResultToResponseQueue(expectedTrainerSummary);

            actualTrainerSummary = trainerService.getTrainerSummaryByUsername(username, year, Month.valueOf(month).getValue(), "authHeader");
        } finally {
            MDC.clear();
        }
    }

    private void sendWorkloadResultToResponseQueue(
            Integer expectedWorkload) {
        jmsTemplate.convertAndSend(trainerSummaryResponseQueue,
                expectedWorkload,
                message -> {
                    message.setJMSCorrelationID(MDC.get("correlationId"));
                    return message;
                });
    }

    @Then("the correct workload data is retrieved")
    public void the_correct_workload_data_is_retrieved() {
        assertEquals(expectedTrainerSummary, actualTrainerSummary);
    }
}
