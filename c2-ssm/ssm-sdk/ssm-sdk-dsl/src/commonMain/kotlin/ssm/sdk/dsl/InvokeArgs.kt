package ssm.sdk.dsl

import ssm.chaincode.dsl.model.uri.ChaincodeUri

data class InvokeArgs(
	val fcn: String,
	val args: List<String>,
)

data class InvokeCommandArgs(
	val cmd: InvokeType,
	val fcn: String,
	val args: List<String>,
	val chaincodeUri: ChaincodeUri?
)


enum class InvokeType(val value: String) {
		QUERY("query"), INVOKE("invoke")
}
