package io.komune.c2.chaincode.api.gateway.blockchain.model

class BlockModel(
	val blockId: Long,
	val previousHash: ByteArray,
	val dataHash: ByteArray,
	val transactions: List<TransactionModel>
)
