Feature: Training Service
  Scenario: 001 - Create a training
    Given a training with trainee username "Jane.Trainee", trainer username "John.Trainer1", training name "Test boxing training", training date "2026-01-05", duration 600, and training type "Boxing"
    When a request to create the training is made
    Then the training is created successfully

  Scenario: 002 - Get trainer trainings by trainee username and date
    When a request to fetch trainings for trainer "John.Trainer" with criteria fromDate "2022-01-01", toDate "2022-01-01" and traineeUserName "Jane.Trainee" is made
    Then a list of trainings matching the criteria is returned

  Scenario: 003 - Get trainer trainings by date
    When a request to fetch trainings for trainer "John.Trainer" with criteria fromDate "2022-01-01", toDate "2022-01-01"
    Then a list of trainings matching the criteria is returned

  Scenario: 004 - Get all trainer trainings by trainer username
    When a request to fetch all trainings for trainer "John.Trainer" is made
    Then a list of trainings matching the criteria is returned

  Scenario: 005 - Get trainee trainings by trainer username and date
    When a request to fetch trainings for trainee "Jane.Trainee" with criteria fromDate "2022-01-01", toDate "2022-01-01" and trainerUserName "John.Trainer" is made
    Then a list of trainings matching the criteria is returned

  Scenario: 006 - Get trainer trainings by date
    When a request to fetch trainings for trainee "Jane.Trainee" with criteria fromDate "2022-01-01", toDate "2022-01-01"
    Then a list of trainings matching the criteria is returned

  Scenario: 007 - Get all trainer trainings by trainee username
    When a request to fetch all trainings for trainee "Jane.Trainee" is made
    Then a list of trainings matching the criteria is returned