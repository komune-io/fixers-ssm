Feature:
  Scenario: As a developer, I want to get all databases
    Given I have a local database
    When I get the list of databases
    Then I found the database "sandbox_ssm"

  Scenario: As a developer, I want to get all databases filter by channelId
    Given I have a local database
    When I get the list of databases filtered by "filter" with "value"
    Then I found the database "sandbox_ssm"

  Scenario: Get database by channelId and chaincodeId
    Given I have a local database
    When I get the database for
      | channelId        | chaincodeId  |
      | sandbox          | ssm          |
    Then I found the database "sandbox_ssm"

  Scenario: Get not existing database
    Given I have a local database
    When I get the database for
      | channelId        | chaincodeId  |
      | no               | exist        |
    Then I do not found database
