package io.komune.c2.chaincode.api.gateway.blockchain

import io.komune.c2.chaincode.api.fabric.FabricChannelClient
import io.komune.c2.chaincode.api.fabric.model.Endorser
import io.komune.c2.chaincode.api.fabric.model.InvokeArgs
import io.komune.c2.chaincode.api.fabric.utils.InvokeArgsUtils
import io.komune.c2.chaincode.api.fabric.utils.JsonUtils
import io.komune.c2.chaincode.api.gateway.blockchain.model.toBlock
import io.komune.c2.chaincode.api.gateway.blockchain.model.toTransaction
import io.komune.c2.chaincode.api.gateway.config.FabricClientBuilder
import io.komune.c2.chaincode.api.gateway.config.FabricClientProvider
import org.hyperledger.fabric.sdk.BlockInfo
import org.hyperledger.fabric.sdk.HFClient
import org.springframework.stereotype.Service

@Service
class BlockchainService(
	private val fabricClientProvider: FabricClientProvider,
	private val fabricClientBuilder: FabricClientBuilder,
) {

	@Suppress("ReturnCount")
	fun query(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, invokeArgs: InvokeArgs): String {
		val isList = InvokeArgsUtils.isListQuery(invokeArgs)

		if (InvokeArgsUtils.isBlockQuery(invokeArgs)) {
			if (isList) {
				return queryAllBlocks(channelId)
			}
			return queryBlockByNumber(channelId, invokeArgs)
		}

		if (InvokeArgsUtils.isTransactionQuery(invokeArgs)) {
			if (isList) {
				return queryAllTransactions(channelId)
			}
			return queryTransactionById(channelId, invokeArgs)
		}
		throw IllegalArgumentException(
			"invokeArgs[${invokeArgs}] must be ${InvokeArgsUtils.BLOCK_QUERY} or ${InvokeArgsUtils.TRANSACTION_QUERY}"
		)
	}

	fun queryAllBlocks(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId): String {
		return getAllBlockIds(channelId).let(JsonUtils::toJson)
	}

	private fun getAllBlockIds(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId): List<Long> =
		queryChannel(channelId) { fabricChannelClient, endorsers, hfClient ->
		val nbBlocks = fabricChannelClient.queryBlockCount(endorsers, hfClient, channelId)
		(0 until nbBlocks).toList()
	}

	fun queryBlockByNumber(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, invokeArgs: InvokeArgs): String {
		return queryBlockByNumber(channelId, invokeArgs.values.first().toLong())
			.toBlock()
			.let(JsonUtils::toJson)
	}

	private fun queryBlockByNumber(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, blockNumber: Long): BlockInfo
	= queryChannel(channelId) { fabricChannelClient, endorsers, hfClient ->
		fabricChannelClient.queryBlockByNumber(endorsers, hfClient, channelId, blockNumber)
	}

	fun queryAllTransactions(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId): String {
		return getAllBlockIds(channelId)
			.flatMap { blockId -> queryTransactionIdsOfBlock(channelId, blockId) }
			.let(JsonUtils::toJson)
	}

	private fun queryTransactionIdsOfBlock(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, blockNumber: Long): List<String> {
		try {
			val block = queryBlockByNumber(channelId, blockNumber)
			return block.envelopeInfos
				.map(BlockInfo.EnvelopeInfo::getTransactionID)
				.filter(String::isNotBlank)
		} catch (e: Exception) {
			throw IllegalArgumentException("Error while fetching block [$blockNumber] of channel [$channelId]")
		}
	}

	fun queryTransactionById(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, invokeArgs: InvokeArgs): String {
		val transactionId = invokeArgs.values.first()
		val block = queryBlockByTransactionId(channelId, transactionId)
		return block.envelopeInfos
			.find { it.transactionID == transactionId }
			?.toTransaction(block)
			.let(JsonUtils::toJson)
	}

	private fun queryBlockByTransactionId(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, txID: String): BlockInfo
	= queryChannel(channelId) { fabricChannelClient, endorsers, hfClient ->
		fabricChannelClient.queryBlockByTransactionId(endorsers, hfClient, channelId, txID)
	}

	private fun <T> queryChannel(channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId, query: (FabricChannelClient, List<Endorser>, HFClient) -> T): T {
		val client = fabricClientProvider.get(channelId)
		val channelConfig = fabricClientBuilder.getChannelConfig(channelId)
		val fabricChannelClient = fabricClientBuilder.getFabricChannelClient(channelId)
		return query(fabricChannelClient, channelConfig.endorsers, client)
	}
}
