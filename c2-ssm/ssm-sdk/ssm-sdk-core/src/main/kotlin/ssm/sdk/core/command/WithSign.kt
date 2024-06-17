package ssm.sdk.core.command

import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.uri.ChaincodeUri

interface WithSign {
    val chaincodeUri: ChaincodeUri
    val signerName: AgentName
}
