package io.komune.c2.chaincode.api.gateway.blockchain.model

import org.hyperledger.fabric.sdk.BlockInfo

class BlockModel(
	val blockId: Long,
	val previousHash: ByteArray,
	val dataHash: ByteArray,
	val transactions: List<TransactionModel>
)

fun BlockInfo.toBlock(): BlockModel {
	return BlockModel(
		blockId = this.blockNumber,
		previousHash = this.previousHash,
		dataHash = this.dataHash,
		transactions = this.envelopeInfos.map { it.toTransaction(this) }
	)
}

fun BlockInfo.EnvelopeInfo.toTransaction(block: BlockInfo): TransactionModel {
	return TransactionModel(
		transactionId = this.transactionID,
		blockId = block.blockNumber,
		timestamp = this.timestamp,
		isValid = this.isValid,
		channelId = this.channelId,
		creator = IdentitiesInfoModel(
			mspid = this.creator.mspid,
			id = this.creator.id
		),
		nonce = this.nonce,
		type = this.type,
		validationCode = this.validationCode
	)
}
