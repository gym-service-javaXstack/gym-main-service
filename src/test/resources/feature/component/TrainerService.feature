Feature: Trainer Service

  Scenario Outline: 001 - Create a new trainer
    Given the trainer data as: "<firstName>", "<lastName>", "<specialization>"
    When a request to create the trainer is made
    Then the response should contain the username "<username>"
    * the password in the response has a length of 10

    Examples:
      | firstName | lastName | specialization | username       |
      | John      | Test     | Test           | John.Test      |
      | Michael   | Sobur    | Test           | Michael.Sobur  |
      | Michael   | Sobur    | Test           | Michael.Sobur1 |
      | Michael   | Sobur    | Test           | Michael.Sobur2 |

  Scenario: 002 - Get a existing trainer
    Given a trainer with username "John.Trainer"
    When a request to fetch the trainer data is made
    Then the expected trainer data is returned

  Scenario: 003 - Get a non existing trainer with EntityNotFoundException
    Given a trainer with username "Non.Exist"
    When a request to fetch the non exist trainer data is made
    Then an EntityNotFoundException is thrown

  Scenario: 004 - Update a trainer
    Given a trainer with username "John.Trainer"
    * data to update the trainer: first name "Jonathan", last name "Manager", training type "Boxing"
    When a request to update the trainer data is made
    Then the updated trainer data is returned

  Scenario: 005 - Attempt to update a non-existent trainer
    Given a trainer with username "Non.Exist"
    When an attempt is made to update the trainer's data
    Then an EntityNotFoundException is thrown

  Scenario: 006 - Get trainers by username list
    Given a list of trainer usernames "John.Trainer", "John.Trainer1", "Alice.Trainer"
    When a request to fetch trainers by username list is made
    Then a list of trainers with the given usernames is returned

  Scenario: 007 - Get trainer summary by username, year, and month
    Given a trainer with username "John.Trainer" has 120 working hours for the month "MARCH" in 2024
    When a request to retrieve workload by username "John.Trainer" for "MARCH" 2024 is made
    Then the correct workload data is retrieved
