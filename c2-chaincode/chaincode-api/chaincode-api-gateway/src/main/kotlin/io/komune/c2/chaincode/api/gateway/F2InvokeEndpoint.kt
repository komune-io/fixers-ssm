package io.komune.c2.chaincode.api.gateway

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.operators.batch
import io.komune.c2.chaincode.api.fabric.FabricGatewayClientSuspend
import io.komune.c2.chaincode.api.fabric.model.InvokeArgs
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeParams
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeReturn
import io.komune.c2.chaincode.api.gateway.chaincode.model.toInvokeArgs
import io.komune.c2.chaincode.api.gateway.config.ChainCodeId
import io.komune.c2.chaincode.api.gateway.config.ChannelChaincode
import io.komune.c2.chaincode.api.gateway.config.ChannelId
import io.komune.c2.chaincode.api.gateway.config.FabricClientBuilder
import io.komune.c2.chaincode.api.gateway.config.HeraclesConfigProps
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class F2InvokeEndpoint(
	private val fabricClientBuilder: FabricClientBuilder,
	private val coopConfigProps: HeraclesConfigProps,
) {

    private val logger = LoggerFactory.getLogger(F2InvokeEndpoint::class.java)

	@Bean
	fun invokeF2(): F2Function<InvokeParams, InvokeReturn> = F2Function { args ->
        args.batch(coopConfigProps.getBatch()) { list ->
			logger.debug("Invoking chaincode ${list.size} items")
			list.groupBy {
				coopConfigProps.getChannelChaincodePair(it.channelid, it.chaincodeid)
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
		chainCodeId: ChainCodeId,
		invokeArgs: List<InvokeArgs>,
	): List<InvokeReturn> = coroutineScope {
		val channelConfig = fabricClientBuilder.getChannelConfig(channelId)
		val fabricChainCodeClientSuspend = getFabricGatewayClientSuspend(channelConfig)

		invokeArgs.let {
			fabricChainCodeClientSuspend.invoke(
				endorsers = channelConfig.endorsers,
				orgName =  channelConfig.user.org,
				channelName = channelId,
				chainId = chainCodeId,
				invokeArgsList = it
			).map {
				InvokeReturn("SUCCESS", "", it.transactionId)
			}
		}
	}

	fun getFabricGatewayClientSuspend(
		channelConfig: ChannelChaincode,
	): FabricGatewayClientSuspend {
		val fabricConfig = fabricClientBuilder.getFabricConfig(channelConfig.channelId)
		return FabricGatewayClientSuspend(
			cryptoConfigBase = channelConfig.config.crypto,
			fabricConfig = fabricConfig
		)
	}
}
