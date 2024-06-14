package ssm.sdk.core.command

import ssm.chaincode.dsl.model.Agent

data class UserRegisterCommand(
    val agent: Agent,
)
