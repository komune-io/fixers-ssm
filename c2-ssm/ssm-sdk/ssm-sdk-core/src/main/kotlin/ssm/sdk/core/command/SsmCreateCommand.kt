package ssm.sdk.core.command

import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.uri.ChaincodeUri

data class SsmCreateCommand(
    override val chaincodeUri: ChaincodeUri,
    override val signerName: AgentName,
    val ssm: Ssm,
): WithSign
