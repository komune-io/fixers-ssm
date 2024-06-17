package ssm.sdk.core.command

import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.uri.ChaincodeUri

data class UserRegisterCommand(
    override val chaincodeUri: ChaincodeUri,
    override val signerName: AgentName,
    val agent: Agent,
): WithSign
