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