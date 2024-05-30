Feature: Trainer API

  Background: 000 - Authenticate trainer before tests
    Given the user with credentials as below:
      | username | John.Trainer |
      | password | test         |
    When the user calls end point in order to authenticate
    Then the authentication response returned with status code as 200

  Scenario Outline: 001 - Selecting a trainer
    When the user calls authorized end point "/api/v1/trainer?username=<trainerUsername>" with method as 'GET'
    Then the response returned with status code as 200
    * the response data should include the first name "<firstName>" and last name "<lastName>"

    Examples:
      | trainerUsername    | firstName | lastName  |
      | John.Trainer       | John      | Trainer   |
