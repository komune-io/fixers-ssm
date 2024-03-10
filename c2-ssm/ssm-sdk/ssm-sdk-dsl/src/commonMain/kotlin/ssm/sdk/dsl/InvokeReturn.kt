package ssm.sdk.dsl

import ssm.chaincode.dsl.blockchain.TransactionId

data class InvokeReturn(
	val status: String,
	val info: String,
	val transactionId: TransactionId,
)
