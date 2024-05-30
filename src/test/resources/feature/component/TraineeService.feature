Feature: Trainee Service

  Scenario Outline: 001 - Create a new trainee
    Given the trainee data as: "<firstName>", "<lastName>", "<address>", "<dateOfBirth>"
    When a request to create the trainee is made
    Then the response should contain the username "<username>"
    * the password in the response has a length of 10

    Examples:
      | firstName | lastName | address         | dateOfBirth | username         |
      | Joshua    | Trainee  | 123 Main St     | 1990-01-01  | Joshua.Trainee   |
      | Michael   | Trainee  | 456 Market St   | 1992-02-02  | Michael.Trainee  |
      | Michael   | Trainee  | 789 Central Ave | 1994-03-03  | Michael.Trainee1 |
      | Michael   | Trainee  | 321 Oak St      | 1996-04-04  | Michael.Trainee2 |

  Scenario Outline: 002 - Update an existing trainee
    Given an existing trainee with username "<username>"
    And the updated trainee data as: "<firstName>", "<lastName>", "<address>", "<dateOfBirth>", "<isActive>"
    When a request to update the trainee is made
    Then the trainee with username "<username>" should have the updated data

    Examples:
      | username        | firstName | lastName | address       | dateOfBirth | isActive |
      | Joshua.Trainee  | Josh      | Trainee  | 123 Main St   | 1990-01-01  | true     |
      | Michael.Trainee | Mike      | Trainee  | 456 Market St | 1992-02-02  | true     |

  Scenario: 003 - Get a trainee by username
    Given an existing trainee with username "Jane.Trainee"
    When a request to get the trainee is made
    Then the trainee with "Jane.Trainee" should be returned

  Scenario: 004 - Get a non-existent trainee
    Given a non-existent trainee with username "Non.Exist"
    When a request to get the non-existent trainee is made
    Then an EntityNotFoundException is thrown

  Scenario: 005 - Get trainers not assigned to an existing trainee
    Given an existing trainee with username "Jane.Trainee"
    When a request to get trainers not assigned to the trainee is made
    Then a list of trainers not assigned to "Jane.Trainee" should be returned

  Scenario: 006 - Get trainers not assigned to a non-existent trainee
    Given a non-existent trainee with username "Non.Exist"
    When a request to get trainers not assigned to the non-existent trainee is made
    Then an EntityNotFoundException is thrown

  Scenario: 007 - Successfully delete an existing trainee
    Given an existing trainee with username "Delete.Trainee"
    When a request to delete the trainee with username "Delete.Trainee" is made
    Then the trainee with username "Delete.Trainee" should be deleted

  Scenario: 008 - Fail to delete a non-existing trainee
    Given a non-existent trainee with username "Non.Exist"
    When a request to delete the trainee with username "Non.Exist" is made
    Then an EntityNotFoundException is thrown