package ssm.sdk.dsl

import io.komune.c2.chaincode.api.dsl.TransactionId

data class InvokeReturn(
	val status: String,
	val info: String,
	val transactionId: TransactionId,
)
