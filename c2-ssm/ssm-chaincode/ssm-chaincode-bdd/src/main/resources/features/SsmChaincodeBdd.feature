Feature:

  Background:
    When I get the list of user
    When I get the list of admin
    When I get the list of ssm
    When I get the list of session

    Then Admin "ADMIN_NAME" is in the list
    Then User "USER_NAME" is in the list
    Then Ssm "SSM_NAME" is in the list
    Then Session "SESSION_NAME" is in the list
    Then Session "SESSION_NAME" have current state origin "1" current "2" iteration "3"

    Then Admin "ADMIN_NAME" is not in the list
    Then User "USER_NAME" is not in the list
    Then Ssm "SSM_NAME" is not in the list

    Then The action "ACTION_NAME" has been performed for session "SESSION_NAME"
    Then The session "SESSION_NAME" have log with "number" entries
    Then The session "SESSION_NAME" have logs
      | current | iteration | origin | ssm          | sessionName      |
      | 0       | 0         |        | cucumber-ssm | cucumber-session |


    Then Ssm "SsmName" is in the list
    Then Session "SessionName" is in the list
    Then Session "SessionName" have current state origin "1" current "2" iteration "3"