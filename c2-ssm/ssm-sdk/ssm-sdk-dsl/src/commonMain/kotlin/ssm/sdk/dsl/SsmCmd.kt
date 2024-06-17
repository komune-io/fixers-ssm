package ssm.sdk.dsl

import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.uri.ChaincodeUri

data class SsmCmd(
	val chaincodeUri: ChaincodeUri,
	val agentName: AgentName,
	val json: String,
	val command: SsmCmdName,
	val performAction: String? = null,
	val valueToSign: String,
)
