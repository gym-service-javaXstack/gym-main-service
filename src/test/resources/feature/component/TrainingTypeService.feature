Feature: Training Type Service

  Scenario: 001 - Select all training types
    When a request to fetch all training types is made
    Then a list of all training types is returned

  Scenario: 002 - Finding TrainingType by valid name
    When a request to fetch training type by valid name "Test" is made
    Then a training type of required name is returned

  Scenario: 003 - Finding TrainingType by invalid name
    When a request to fetch training type by invalid name "Invalid" is made
    Then an EntityNotFoundException is thrown