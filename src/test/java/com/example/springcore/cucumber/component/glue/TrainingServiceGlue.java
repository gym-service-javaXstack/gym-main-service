package com.example.springcore.cucumber.component.glue;

import com.example.springcore.cucumber.component.CucumberComponentContext;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Training;
import com.example.springcore.service.TrainingService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainingServiceGlue {

    @Autowired
    private CucumberComponentContext context;

    @Autowired
    TrainingService trainingService;

    private List<Training> trainings;
    private String traineeUsername;
    private String trainerUsername;

    @Given("a training with trainee username {string}, trainer username {string}, training name {string}, training date {string}, duration {int}, and training type {string}")
    public void a_training_with_trainee_username_trainer_username_training_name_training_date_and_duration(
            String traineeUsername,
            String trainerUsername,
            String trainingName,
            String trainingDate,
            int duration,
            String trainingTypeName
    ) {

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTraineeUserName(traineeUsername);
        trainingDTO.setTrainerUserName(trainerUsername);
        trainingDTO.setTrainingName(trainingName);
        trainingDTO.setTrainingDate(LocalDate.parse(trainingDate));
        trainingDTO.setDuration(duration);
        trainingDTO.setTrainingTypeName(trainingTypeName);
        context.setTrainingDTO(trainingDTO);
    }

    @When("a request to create the training is made")
    public void a_request_to_create_the_training_is_made() {
        trainingService.createTraining(context.getTrainingDTO());
    }

    @Then("the training is created successfully")
    public void the_training_is_created_successfully() {
        TrainingDTO trainingDTO = context.getTrainingDTO();

        List<Training> trainerTrainingsByCriteria = trainingService.getTrainerTrainingsByCriteria(
                trainingDTO.getTrainerUserName(),
                trainingDTO.getTrainingDate(),
                trainingDTO.getTrainingDate(),
                trainingDTO.getTraineeUserName()
        );
        List<Training> traineeTrainingsByCriteria = trainingService.getTraineeTrainingsByCriteria(
                trainingDTO.getTraineeUserName(),
                trainingDTO.getTrainingDate(),
                trainingDTO.getTrainingDate(),
                trainingDTO.getTrainerUserName(),
                trainingDTO.getTrainingTypeName()
        );

        assertNotNull(trainerTrainingsByCriteria);
        assertNotNull(traineeTrainingsByCriteria);

        // Check that both lists have exactly one element
        assertEquals(1, trainerTrainingsByCriteria.size());
        assertEquals(1, traineeTrainingsByCriteria.size());

        // Check that the usernames in the lists match the usernames in trainingDTO
        assertEquals(trainingDTO.getTraineeUserName(), trainerTrainingsByCriteria.get(0).getTrainee().getUser().getUserName());
        assertEquals(trainingDTO.getTrainerUserName(), traineeTrainingsByCriteria.get(0).getTrainer().getUser().getUserName());
    }

    @When("a request to fetch trainings for trainer {string} with criteria fromDate {string}, toDate {string} and traineeUserName {string} is made")
    public void a_request_to_fetch_trainings_for_trainer_with_criteria(String username, String fromDate, String toDate, String traineeUsernameFilter) {
        trainerUsername = username;
        traineeUsername = traineeUsernameFilter;
        context.setFromDate(LocalDate.parse(fromDate));
        context.setToDate(LocalDate.parse(toDate));
        trainings = trainingService.getTrainerTrainingsByCriteria(username, LocalDate.parse(fromDate), LocalDate.parse(toDate), traineeUsernameFilter);
    }

    @When("a request to fetch trainings for trainer {string} with criteria fromDate {string}, toDate {string}")
    public void a_request_to_fetch_trainings_for_trainer_with_criteria(String username, String fromDate, String toDate) {
        trainerUsername = username;
        context.setFromDate(LocalDate.parse(fromDate));
        context.setToDate(LocalDate.parse(toDate));
        trainings = trainingService.getTrainerTrainingsByCriteria(username, LocalDate.parse(fromDate), LocalDate.parse(toDate), null);
    }

    @Then("a list of trainings matching the criteria is returned")
    public void a_list_of_trainings_matching_the_criteria_is_returned() {
        assertNotNull(trainings);
        assertFalse(trainings.isEmpty());

        boolean allTrainingsMatchCriteria = trainings.stream().allMatch(training -> {
            boolean traineeUsernameMatches = true;
            boolean trainerUsernameMatches = true;
            boolean fromDateMatches = true;
            boolean toDateMatches = true;

            if (training.getTrainer().getUser().getUserName() != null && trainerUsername != null) {
                trainerUsernameMatches = training.getTrainer().getUser().getUserName().equals(trainerUsername);
            }
            if (training.getTrainee().getUser().getUserName() != null && traineeUsername != null) {
                traineeUsernameMatches = training.getTrainee().getUser().getUserName().equals(traineeUsername);
            }
            if (training.getTrainingDate() != null && context.getFromDate() != null) {
                fromDateMatches = !training.getTrainingDate().isBefore(context.getFromDate());
            }
            if (training.getTrainingDate() != null && context.getToDate() != null) {
                toDateMatches = !training.getTrainingDate().isAfter(context.getToDate());
            }

            return traineeUsernameMatches && fromDateMatches && toDateMatches && trainerUsernameMatches;
        });

        assertTrue(allTrainingsMatchCriteria, "Not all trainings match the criteria");
    }

    @When("a request to fetch all trainings for trainer {string} is made")
    public void a_request_to_fetch_all_trainings_for_trainer(String username) {
        trainerUsername = username;
        trainings = trainingService.getTrainerTrainingsByCriteria(username, null, null, null);
    }

    @When("a request to fetch all trainings for trainee {string} is made")
    public void a_request_to_fetch_all_trainings_for_trainee(String username) {
        traineeUsername = username;
        trainings = trainingService.getTraineeTrainingsByCriteria(username, null, null, null, null);
    }

    @When("a request to fetch trainings for trainee {string} with criteria fromDate {string}, toDate {string} and trainerUserName {string} is made")
    public void a_request_to_fetch_trainings_for_trainee_with_criteria(String username, String fromDate, String toDate, String trainerUsernameFilter) {
        traineeUsername = username;
        trainerUsername = trainerUsernameFilter;
        context.setFromDate(LocalDate.parse(fromDate));
        context.setToDate(LocalDate.parse(toDate));
        trainings = trainingService.getTraineeTrainingsByCriteria(username, LocalDate.parse(fromDate), LocalDate.parse(toDate), trainerUsernameFilter, null);
    }

    @When("a request to fetch trainings for trainee {string} with criteria fromDate {string}, toDate {string}")
    public void a_request_to_fetch_trainings_for_trainee_with_criteria(String username, String fromDate, String toDate) {
        traineeUsername = username;
        context.setFromDate(LocalDate.parse(fromDate));
        context.setToDate(LocalDate.parse(toDate));
        trainings = trainingService.getTraineeTrainingsByCriteria(username, LocalDate.parse(fromDate), LocalDate.parse(toDate), null, null);
    }
}
