package ssm.sdk.sign

import ssm.chaincode.dsl.model.AgentName
import ssm.sdk.dsl.SsmCmd
import ssm.sdk.dsl.SsmCmdSigned

interface SsmCmdSigner {
	fun sign(ssmCommand: SsmCmd, agentName: AgentName): SsmCmdSigned
}
