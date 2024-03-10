Feature:

  Scenario: Verify admin exist
    When I get the list of admin
    Then Admin "ssm-admin" is in the list

  Scenario: Verify user exist
    When I get the list of user
    Then User "bob" is not in the list

  Scenario: Register a user
    Given An admin
    When I register a user "cucumber-user"
    And I get the list of user
    Then User "cucumber-user" is in the list

  Scenario: Create a ssm
    Given An admin
    When I create a ssm "loop-ssm" with transitions
      | from | to | role   | action |
      | 0    | 0  | Looper | Loop   |
    And I get the list of ssm
    Then Ssm "loop-ssm" is in the list

  Scenario: Start a ssm session
    Given An admin
    Given A new user "Admin"
    And A ssm "crud-ssm" with transitions
      | from | to | role   | action |
      | 0    | 1  | Issuer | Create |
      | 1    | 1  | Issuer | Update |
      | 1    | 2  | Issuer | Delete |
    When I start a session "crud-ssm-session" for "crud-ssm"
      | role  | userName |
      | Admin | Issuer   |
    Then The session "crud-ssm-session" for "crud-ssm" have logs
      | current | iteration | originAction | originRole | originFrom | originTo |
      | 0       | 0         |              |            |            |          |

  Scenario: Do transition
    Given An admin
    Given A new user "Looper"
    Given A new user "Engineer"
    And A ssm "loop-ssm" with transitions
      | from | to | role    | action |
      | 0    | 0  | Looping | Loop   |
      | 0    | 1  | Engine  | Exit   |
    When I start a session "loop-ssm-session" for "loop-ssm"
      | role    | userName |
      | Looping | Looper   |
      | Engine  | Engineer |
    And I perform actions
      | action | iteration | sessionName      | userName | public        |
      | Loop   | 0         | loop-ssm-session | Looper   | A loop        |
      | Loop   | 1         | loop-ssm-session | Looper   | An other loop |
      | Loop   | 2         | loop-ssm-session | Looper   | An other loop |
      | Exit   | 3         | loop-ssm-session | Engineer | No more loop  |
    Then The session "loop-ssm-session" for "loop-ssm" have logs
      | current | iteration | originAction | originRole | originFrom | originTo |
      | 0       | 0         |              |            |            |          |
      | 0       | 1         | Loop         | Looping    | 0          | 0        |
      | 0       | 2         | Loop         | Looping    | 0          | 0        |
      | 0       | 3         | Loop         | Looping    | 0          | 0        |
      | 1       | 4         | Exit         | Engine     | 0          | 1        |