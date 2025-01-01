package io.komune.c2.chaincode.api.gateway.chaincode

import io.komune.c2.chaincode.api.config.C2ChaincodeConfiguration
import io.komune.c2.chaincode.api.config.ChannelConfig
import io.komune.c2.chaincode.api.config.FabricConfigLoader
import io.komune.c2.chaincode.api.dsl.ChaincodeId
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgs
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgsUtils
import io.komune.c2.chaincode.api.fabric.FabricGatewayBuilder
import io.komune.c2.chaincode.api.fabric.FabricGatewayClient
import io.komune.c2.chaincode.api.gateway.blockchain.BlockchainServiceI
import io.komune.c2.chaincode.api.gateway.chaincode.model.Cmd
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeParams
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeReturn
import io.komune.c2.chaincode.api.gateway.chaincode.model.toInvokeArgs
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service

@Service
class ChaincodeService(
	val blockchainService: BlockchainServiceI,
	private val chaincodeConfiguration: C2ChaincodeConfiguration,
	private val fabricConfigLoader: FabricConfigLoader,
) {

	suspend fun execute(args: InvokeParams): String {
		val chainCodePair = chaincodeConfiguration.getChannelChaincodePair(args.channelid, args.chaincodeid)
		val invokeArgs = args.toInvokeArgs()
		return when (args.cmd) {
			Cmd.invoke -> doInvoke(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs).await()
			Cmd.query -> doQuery(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs).await()
		}
	}

	suspend fun execute(args: List<InvokeParams>): List<Any> {
		val futureList = args.map { params ->
			val chainCodePair = chaincodeConfiguration.getChannelChaincodePair(params.channelid, params.chaincodeid)
			val invokeArgs = InvokeArgs(params.fcn, params.args.toList())
			when (params.cmd) {
				Cmd.invoke -> doInvoke(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs)
				Cmd.query -> doQuery(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs)
			}
		}
		return futureList.awaitAll()
	}

	private suspend fun doQuery(
		channelId: ChannelId,
		chainCodeId: ChaincodeId,
		invokeArgs: InvokeArgs,
	): Deferred<String>  = coroutineScope {
		if (InvokeArgsUtils.isBlockQuery(invokeArgs) || InvokeArgsUtils.isTransactionQuery(invokeArgs)) {
			async { blockchainService.query(channelId, invokeArgs) }
		} else {
			async { doQueryChaincode(channelId, chainCodeId, invokeArgs) }
		}
	}

	private suspend fun doQueryChaincode(
		channelId: ChannelId,
		chainCodeId: ChaincodeId,
		invokeArgs: InvokeArgs,
	): String {
		val channelConfig = fabricConfigLoader.getChannelConfig(channelId)
		val fabricChainCodeClientSuspend = getFabricGatewayClientSuspend(channelConfig)
		return fabricChainCodeClientSuspend.query(
			endorsers = channelConfig.endorsers,
			orgName =  channelConfig.user.org,
			channelId = channelId,
			chaincodeId = chainCodeId,
			invokeArgsList = listOf(invokeArgs)
		).first()
	}

	private suspend fun doInvoke(
		channelId: ChannelId,
		chainCodeId: ChaincodeId,
		invokeArgs: InvokeArgs,
	): Deferred<String> = coroutineScope {
		async {
			doInvoke(channelId, chainCodeId, listOf(invokeArgs)).first().toJson()
		}
	}

	private suspend fun doInvoke(
		channelId: ChannelId,
		chainCodeId: ChaincodeId,
		invokeArgs: List<InvokeArgs>,
	): List<InvokeReturn> = coroutineScope {
		val channelConfig = fabricConfigLoader.getChannelConfig(channelId)
		val fabricChainCodeClientSuspend = getFabricGatewayClientSuspend(channelConfig)
		fabricChainCodeClientSuspend.invoke(
			endorsers = channelConfig.endorsers,
			orgName =  channelConfig.user.org,
			channelId = channelId,
			chaincodeId = chainCodeId,
			invokeArgsList = invokeArgs
		).map {
			InvokeReturn("SUCCESS", "", it.transactionId)
		}
	}

	private fun getFabricGatewayClientSuspend(
		channelConfig: ChannelConfig,
	): FabricGatewayClient {
		val builder = FabricGatewayBuilder(
			cryptoConfigBase = channelConfig.config.crypto,
			fabricConfigLoader = fabricConfigLoader
		)
		return FabricGatewayClient(
			fabricGatewayBuilder = builder
		)
	}
}
