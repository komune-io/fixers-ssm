package io.komune.c2.chaincode.api.gateway.chaincode

import io.komune.c2.chaincode.api.gateway.config.ChainCodeId
import io.komune.c2.chaincode.api.gateway.config.ChannelId
import io.komune.c2.chaincode.api.fabric.model.InvokeArgs
import io.komune.c2.chaincode.api.fabric.utils.InvokeArgsUtils
import io.komune.c2.chaincode.api.gateway.blockchain.BlockchainService
import io.komune.c2.chaincode.api.gateway.chaincode.model.Cmd
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeParams
import io.komune.c2.chaincode.api.gateway.chaincode.model.InvokeReturn
import io.komune.c2.chaincode.api.gateway.config.FabricClientBuilder
import io.komune.c2.chaincode.api.gateway.config.FabricClientProvider
import io.komune.c2.chaincode.api.gateway.config.HeraclesConfigProps
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class ChaincodeService(
	val blockchainService: BlockchainService,
	val coopConfigProps: HeraclesConfigProps,
	val fabricClientProvider: FabricClientProvider,
	val fabricClientBuilder: FabricClientBuilder,
) {

	fun execute(args: InvokeParams): CompletableFuture<String> {
		val chainCodePair = coopConfigProps.getChannelChaincodePair(args.channelid, args.chaincodeid)
		val invokeArgs = InvokeArgs(args.fcn, args.args.iterator())
		return when (args.cmd) {
			Cmd.invoke -> doInvoke(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs)
			Cmd.query -> doQuery(chainCodePair.channelId, chainCodePair.chainCodeId, invokeArgs)
		}
	}

	private fun doQuery(
        channelId: ChannelId,
        chainCodeId: ChainCodeId?,
        invokeArgs: InvokeArgs,
	): CompletableFuture<String> {
		if (InvokeArgsUtils.isBlockQuery(invokeArgs) || InvokeArgsUtils.isTransactionQuery(invokeArgs)) {
			return CompletableFuture.completedFuture(
				blockchainService.query(channelId, invokeArgs)
			)
		}

		return doQueryChaincode(channelId, chainCodeId, invokeArgs)
	}

	private fun doQueryChaincode(
        channelId: ChannelId,
        chainCodeId: ChainCodeId?,
        invokeArgs: InvokeArgs,
	): CompletableFuture<String> {
		val client = fabricClientProvider.get(channelId)
		val channelConfig = fabricClientBuilder.getChannelConfig(channelId)
		val fabricChainCodeClient = fabricClientBuilder.getFabricChainCodeClient(channelId)
		return CompletableFuture.completedFuture(
			fabricChainCodeClient.query(channelConfig.endorsers, client, channelId, chainCodeId, invokeArgs)
		)
	}

	private fun doInvoke(
        channelId: ChannelId,
        chainCodeId: ChainCodeId,
        invokeArgs: InvokeArgs,
	): CompletableFuture<String> {
		val client = fabricClientProvider.get(channelId)
		val channelConfig = fabricClientBuilder.getChannelConfig(channelId)
		val fabricChainCodeClient = fabricClientBuilder.getFabricChainCodeClient(channelId)
		val future = fabricChainCodeClient.invoke(channelConfig.endorsers, client, channelId, chainCodeId, invokeArgs)
		return future.thenApply {
			InvokeReturn("SUCCESS", "", it.transactionID).toJson()
		}
	}
}
