package ssm.sdk.core.command

import ssm.chaincode.dsl.model.SsmContext

data class SsmPerformCommand(
    val action: String,
    val context: SsmContext,
)
