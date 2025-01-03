package io.komune.c2.chaincode.api.fabric

import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.Endorser
import io.komune.c2.chaincode.api.dsl.IdentitiesInfo
import io.komune.c2.chaincode.api.dsl.TransactionId
import org.hyperledger.fabric.protos.common.Block
import org.hyperledger.fabric.protos.common.BlockchainInfo
import org.hyperledger.fabric.protos.common.ChannelHeader
import org.hyperledger.fabric.protos.common.Envelope
import org.hyperledger.fabric.protos.common.Payload
import org.hyperledger.fabric.protos.common.SignatureHeader
import org.hyperledger.fabric.protos.msp.SerializedIdentity
import org.hyperledger.fabric.protos.peer.ProcessedTransaction
import io.komune.c2.chaincode.api.dsl.Block as BlockDsl
import io.komune.c2.chaincode.api.dsl.Transaction as TransactionDsl


class FabricGatewayBlockClient(
    private val fabricGatewayBuilder: FabricGatewayBuilder,
    private val organizationName: String,
    private val endorsers: List<Endorser>
) {
//    val GETCHAININFO: String = "GetChainInfo"
//    val GETBLOCKBYNUMBER: String = "GetBlockByNumber"
//    val GETBLOCKBYHASH: String = "GetBlockByHash"
//    val GETTRANSACTIONBYID: String = "GetTransactionByID"
//    val GETBLOCKBYTXID: String = "GetBlockByTxID"


    fun queryAllBlocksIds(channelId: ChannelId): List<Long> {
        val gateway = fabricGatewayBuilder.gateway(organizationName, channelId, endorsers)
        return gateway.use { gateway ->
            val network = gateway.getNetwork(channelId)
            val contract = network.getContract("qscc")
            val blockIds = contract.evaluateTransaction("GetChainInfo", channelId)
            val height = BlockchainInfo.parseFrom(blockIds).height
            (0..height-1).toList()
        }

    }

    fun queryBlockByNumber(channelId: ChannelId, blockId: Long): BlockDsl {
        val gateway = fabricGatewayBuilder.gateway(organizationName, channelId, endorsers)
        return gateway.use {
            val network = gateway.getNetwork(channelId)
            val contract = network.getContract("qscc")
            val blockIds = contract.evaluateTransaction("GetBlockByNumber", channelId, blockId.toString())
            val result = Block.parseFrom(blockIds)
            val transactions = result.data.dataList.map { data ->

                val envelopeInfo: Envelope = Envelope.parseFrom(data)

                val payload = Payload.parseFrom(envelopeInfo.payload)
                val signatureHeader = SignatureHeader.parseFrom(payload.header.signatureHeader)
                val channelHeader = ChannelHeader.parseFrom(payload.header.channelHeader)

                val identifierHeader = SerializedIdentity.parseFrom(signatureHeader.creator)
                @Suppress("MagicNumber")
                val millis: Long = channelHeader.timestamp.seconds * 1000 + channelHeader.timestamp.nanos / 1000000

                TransactionDsl(
                    transactionId = channelHeader.txId,
                    blockId = blockId.toInt(),
                    timestamp = millis,
                    isValid = true,
                    channelId = channelHeader.channelId,
                    creator = IdentitiesInfo(
                        mspid = identifierHeader.mspid,
                        id = String(identifierHeader.idBytes.toByteArray(), Charsets.UTF_8)
                    ),
                    nonce = signatureHeader.nonce.toByteArray(),
                )
            }
            BlockDsl(
                blockId = result.header.number.toInt(),
                transactions = transactions,
                previousHash = result.header.previousHash.toByteArray(),
                dataHash = result.header.dataHash.toByteArray()
            )
        }
    }

    fun queryTransactionById(channelId: ChannelId, transactionId: TransactionId): TransactionDsl {
        val gateway = fabricGatewayBuilder.gateway(organizationName, channelId, endorsers)
        return gateway.use {
            val network = gateway.getNetwork(channelId)
            val contract = network.getContract("qscc")
            val data = contract.evaluateTransaction("GetTransactionByID", channelId, transactionId)
            val processedTransaction = ProcessedTransaction.parseFrom(data)
//            val envelopeInfo = Envelope.parseFrom(data)
            processedTransaction.transactionEnvelope
            val payload = Payload.parseFrom(processedTransaction.transactionEnvelope.payload)
            val signatureHeader = SignatureHeader.parseFrom(payload.header.signatureHeader)
            val channelHeader = ChannelHeader.parseFrom(payload.header.channelHeader)

            val identifierHeader = SerializedIdentity.parseFrom(signatureHeader.creator)
            @Suppress("MagicNumber")
            val millis: Long = channelHeader.timestamp.seconds * 1000 + channelHeader.timestamp.nanos / 1000000

            TransactionDsl(
                transactionId = channelHeader.txId,
                blockId = 0,
                timestamp = millis,
                isValid = true,
                channelId = channelHeader.channelId,
                creator = IdentitiesInfo(
                    mspid = identifierHeader.mspid,
                    id = String(identifierHeader.idBytes.toByteArray(), Charsets.UTF_8)
                ),
                nonce = signatureHeader.nonce.toByteArray(),
            )
        }
    }
}

class Block(
    val number: Long,
    val transactions: List<Transaction>
)




//
//fun BlockInfo.toBlock(): BlockModel {
//	return BlockModel(
//		blockId = this.blockNumber,
//		previousHash = this.previousHash,
//		dataHash = this.dataHash,
//		transactions = this.envelopeInfos.map { it.toTransaction(this) }
//	)
//}
//
//fun BlockInfo.EnvelopeInfo.toTransaction(block: BlockInfo): TransactionModel {
//	return TransactionModel(
//		transactionId = this.transactionID,
//		blockId = block.blockNumber,
//		timestamp = this.timestamp,
//		isValid = this.isValid,
//		channelId = this.channelId,
//		creator = IdentitiesInfoModel(
//			mspid = this.creator.mspid,
//			id = this.creator.id
//		),
//		nonce = this.nonce,
//		type = this.type,
//		validationCode = this.validationCode
//	)
//}
