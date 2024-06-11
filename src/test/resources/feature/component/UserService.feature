Feature: User Service

  Scenario: 001 - Check default Trainee user status
    Given there is a trainee with firstname "test" and lastname "test"
    Then the user's status should be "false"

  Scenario: 002 - Change Trainee user status
    Given there is a trainee with firstname "test" and lastname "test"
    When the user status is changed to "true"
    Then the user's status should be "true"

  Scenario: 003 - Change Trainee user password
    Given there is a trainee with firstname "test" and lastname "test"
    When the user password is changed to "test"
    Then the user's password should be "test"

  Scenario: 004 - Change Trainee user password with IllegalArgumentException
    Given there is a trainee with firstname "test" and lastname "test"
    When a request to change password with incorrect oldPassword "incorrectPassword"
    Then an IllegalArgumentException is thrown

  Scenario Outline: 005 - Fetch usernames by first and last name
    Given creating users with first name "<firstName>" and last name "<lastName>" 3 times
    When a request to fetch usernames by first name "<firstName>" and last name "<lastName>" is made
    Then a list of usernames with the format "<firstName>" and "<lastName>" is returned
    * the response should contain the username "<username>"

    Examples:
      | firstName | lastName   | username        |
      | test      | duplicate  | test.duplicate  |
      | test      | duplicate  | test.duplicate1 |
      | test      | duplicate  | test.duplicate2 |