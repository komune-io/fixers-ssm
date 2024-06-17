package ssm.sdk.core.command

import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.SsmSession
import ssm.chaincode.dsl.model.uri.ChaincodeUri

data class SsmStartCommand(
    override val chaincodeUri: ChaincodeUri,
    override val signerName: AgentName,
    val session: SsmSession,
): WithSign
