package io.komune.c2.chaincode.api.gateway.blockchain

import io.komune.c2.chaincode.api.config.C2ChaincodeConfiguration
import io.komune.c2.chaincode.api.config.FabricConfigLoader
import io.komune.c2.chaincode.api.config.utils.JsonUtils
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgs
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgsUtils
import io.komune.c2.chaincode.api.fabric.FabricGatewayBlockClient
import io.komune.c2.chaincode.api.fabric.FabricGatewayBuilder
import org.springframework.stereotype.Service

@Service
class BlockchainService(
	private val chaincodeConfiguration: C2ChaincodeConfiguration,
	private val fabricConfigLoader: FabricConfigLoader,

) :BlockchainServiceI {

	@Suppress("ReturnCount")
	override fun query(channelId: ChannelId, invokeArgs: InvokeArgs): String {
		val isList = InvokeArgsUtils.isListQuery(invokeArgs)

		if (InvokeArgsUtils.isBlockQuery(invokeArgs)) {
			return if (isList) {
				queryAllBlocks(channelId)
			} else {
				queryBlockByNumber(channelId, invokeArgs)
			}
		}

		if (InvokeArgsUtils.isTransactionQuery(invokeArgs)) {
			return if (isList) {
				queryAllTransactions(channelId)
			} else {
				queryTransactionById(channelId, invokeArgs)
			}
		}
		throw IllegalArgumentException(
			"invokeArgs[${invokeArgs}] must be ${InvokeArgsUtils.BLOCK_QUERY} or ${InvokeArgsUtils.TRANSACTION_QUERY}"
		)
	}

	override fun queryAllBlocks(channelId: ChannelId): String {
		return getFabricGatewayBlockClient().queryAllBlocksIds(channelId).let(JsonUtils::toJson)
	}

	override fun queryBlockByNumber(channelId: ChannelId, invokeArgs: InvokeArgs): String {
		return getFabricGatewayBlockClient()
			.queryBlockByNumber(channelId, invokeArgs.values.first().toLong()).let(JsonUtils::toJson)
	}

	override fun queryAllTransactions(channelId: ChannelId): String {
		val client = getFabricGatewayBlockClient()
		return getFabricGatewayBlockClient().queryAllBlocksIds(channelId)
			.flatMap { blockId ->
				client.queryBlockByNumber(channelId, blockId).transactions
			}
			.let(JsonUtils::toJson)
	}

	override fun queryTransactionById(channelId: ChannelId, invokeArgs: InvokeArgs): String {
		return getFabricGatewayBlockClient().queryTransactionById(channelId, invokeArgs.values.first()).let(JsonUtils::toJson)
	}

	private fun getFabricGatewayBlockClient(): FabricGatewayBlockClient {
		val builder = FabricGatewayBuilder(
			cryptoConfigBase = chaincodeConfiguration.config!!.crypto,
			fabricConfigLoader = fabricConfigLoader
		)

		return FabricGatewayBlockClient(
			organizationName = chaincodeConfiguration.user.org,
			endorsers = chaincodeConfiguration.getEndorsers(),
			fabricGatewayBuilder = builder
		)
	}
}
