Feature:
  Background:
    When I get the list of user
    When I get the list of admin
    When I get the list of ssm
    When I get the list of session
    Then User "UserName" is in the list
    Then Admin "AdminName" is in the list
    Then Ssm "SsmName" is in the list
    Then Session "SessionName" is in the list
    Then Session "SessionName" have current state origin "1" current "2" iteration "3"