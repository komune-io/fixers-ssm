Feature:

  Scenario Outline: Inject ssmTxCreateFunction function
    Given The application parameters
      | ssm.chaincode.url     | http://localhost:9090  |
      | ssm.signer.admin.name | ssm-admin              |
      | ssm.signer.admin.key  | local/admin/ssm-admin |
    When I build a valid spring application context
    Then Instance of "<functionName>" is an injectable bean

    Examples:
      | functionName        |
      | ssmTxCreateFunction |

  Scenario Outline: No inject ssmTxCreateFunction function when config is not provided.
    When I get an empty spring application context
    Then Instance of "<functionName>" is not injectable bean

    Examples:
      | functionName        |
      | ssmTxCreateFunction |
