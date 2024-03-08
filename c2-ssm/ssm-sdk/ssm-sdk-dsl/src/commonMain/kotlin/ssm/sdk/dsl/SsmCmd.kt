package ssm.sdk.dsl

data class SsmCmd(
	val json: String,
	val command: SsmCmdName,
	val performAction: String? = null,
	val valueToSign: String,
)
