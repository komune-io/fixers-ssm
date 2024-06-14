package ssm.sdk.core.command

import ssm.chaincode.dsl.model.SsmSession

data class SsmStartCommand(
    val session: SsmSession,
)
