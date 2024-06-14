package ssm.sdk.core.command

import ssm.chaincode.dsl.model.Ssm

data class SsmCreateCommand(
    val ssm: Ssm,
)
