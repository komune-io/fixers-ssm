Feature:

  Scenario Outline: Inject ssmTxSessionPerformActionFunction function
    Given The application parameters
      | ssm.chaincode.url     | http://localhost:9090  |
      | ssm.signer.user.name | ssm-admin              |
      | ssm.signer.user.key  | local/admin/ssm-admin |
    When I build a valid spring application context
    Then Instance of "<functionName>" is an injectable bean

    Examples:
      | functionName                      |
      | ssmTxSessionPerformActionFunction |

  Scenario Outline: No inject ssmTxSessionPerformActionFunction function when config is not provided.
    When I get an empty spring application context
    Then Instance of "<functionName>" is not injectable bean

    Examples:
      | functionName                      |
      | ssmTxSessionPerformActionFunction |
