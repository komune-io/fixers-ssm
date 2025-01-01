package ssm.sdk.core.command

import io.komune.c2.chaincode.api.dsl.ChaincodeUri
import ssm.chaincode.dsl.model.AgentName

interface WithSign {
    val chaincodeUri: ChaincodeUri
    val signerName: AgentName
}
