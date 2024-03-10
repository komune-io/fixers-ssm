Feature:
#  Background:
#    Given a couchdb configuration for local env
#    And a channel named "sandbox"
#    And a chaincode ssm names "ssm"

  Scenario: As a developer, I want to get all ssm in a database
    Given I have a local database
    When I get all ssm for
      | channelId        | chaincodeId  |
      | sandbox          | ssm          |
    Then I found ssm