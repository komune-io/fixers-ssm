package io.komune.c2.chaincode.api.gateway

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.operators.batch
import io.komune.c2.chaincode.api.config.C2ChaincodeConfiguration
import io.komune.c2.chaincode.api.config.ChannelConfig
import io.komune.c2.chaincode.api.config.FabricConfigLoader
import io.komune.c2.chaincode.api.dsl.ChaincodeId
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgs
import io.komune.c2.chaincode.api.fabric.FabricGatewayBuilder
import io.komune.c2.chaincode.api.fabric.FabricGatewayClient
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeParams
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeReturn
import io.komune.c2.chaincode.api.gateway.chaincode.model.toInvokeArgs
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class F2InvokeEndpoint(
	private val chaincodeConfiguration: C2ChaincodeConfiguration,
	private val fabricConfigLoader: FabricConfigLoader,
) {

    private val logger = LoggerFactory.getLogger(F2InvokeEndpoint::class.java)

	@Bean
	fun invokeF2(): F2Function<InvokeParams, InvokeReturn> = F2Function { args ->
        args.batch(chaincodeConfiguration.getBatch()) { list ->
			logger.debug("Invoking chaincode ${list.size} items")
			list.groupBy {
				chaincodeConfiguration.getChannelChaincodePair(it.channelid, it.chaincodeid)
			}.flatMap { (channelChainCode, invokeParams) ->
				val channelId = channelChainCode.channelId
				val chainCodeId = channelChainCode.chainCodeId
				val invokeArgs = invokeParams.toInvokeArgs()
				doInvoke(channelId, chainCodeId, invokeArgs)
			}
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
