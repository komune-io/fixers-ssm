package ssm.sdk.core.invoke.builder

import ssm.chaincode.dsl.model.AgentName
import ssm.sdk.dsl.SsmCmd
import ssm.sdk.dsl.SsmCmdName
import ssm.sdk.dsl.SsmCmdSigned
import ssm.sdk.json.JsonUtils
import ssm.sdk.sign.SsmCmdSigner

open class CmdBuilder<T>(
	private val value: T,
	private val commandName: SsmCmdName,
	private val performAction: String? = null) {

	operator fun invoke(agentName: AgentName, signer: SsmCmdSigner): SsmCmdSigned {
		val cmd = commandToSign()
		return signer.sign(cmd, agentName)
	}

	fun commandToSign(): SsmCmd {
		val json = JsonUtils.toJson(value)
		val toSign = valueToSign(json)
		return SsmCmd(json = json, command = commandName, performAction = performAction, valueToSign = toSign)
	}

	private fun valueToSign(json: String): String {
		return performAction?.let {
			performAction + json
		} ?: json
	}
}
