package io.komune.c2.chaincode.dsl.invoke

import io.komune.c2.chaincode.dsl.ChaincodeUri


data class InvokeArgs(
	val function: String,
	val values: List<String>,
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

