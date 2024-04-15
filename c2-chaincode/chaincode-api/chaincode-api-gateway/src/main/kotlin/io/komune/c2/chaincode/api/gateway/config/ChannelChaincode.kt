package io.komune.c2.chaincode.api.gateway.config

import io.komune.c2.chaincode.api.fabric.model.Endorser
import org.slf4j.LoggerFactory

class ChannelChaincode(
    val channelId: io.komune.c2.chaincode.api.gateway.config.ChannelId,
    val chaincodeId: List<io.komune.c2.chaincode.api.gateway.config.ChainCodeId>,
    val user: io.komune.c2.chaincode.api.gateway.config.HeraclesConfigProps.UserConfig,
    val config: HeraclesConfigProps.FileConfig,
    val endorsers: List<Endorser>
) {
	companion object
}

class ChannelChaincodePair(
    val channelId: ChannelId,
    val chainCodeId: ChainCodeId
) {
	companion object {
		fun fromConfig(defaultValue: String): ChannelChaincodePair {
			val ccidByChannel = defaultValue.split("/")
			require(ccidByChannel.size == 2) { "Bad ccid argument[${defaultValue}]. Syntax must by channelId/chaincodeId" }
			return ChannelChaincodePair(
                channelId = ccidByChannel[0],
                chainCodeId = ccidByChannel[1]
            )
		}
	}
}

typealias ChannelId = String
typealias TxId = String
typealias BlockId = Long
typealias ChainCodeId = String

fun ChannelChaincode.Companion.fromConfig(
    lines: Array<String>,
    user: HeraclesConfigProps.UserConfig,
    config: HeraclesConfigProps.FileConfig,
    endorsers: List<Endorser>
): Map<ChannelId, ChannelChaincode> {
	return lines.map { line ->
        ChannelChaincodePair.Companion.fromConfig(line)
	}.groupBy(
		{ it.channelId }, { it.chainCodeId }
	).mapValues { (channelId, chaincodeIds) ->
        ChannelChaincode(
            channelId = channelId,
            chaincodeId = chaincodeIds,
            user = user,
            config = config,
            endorsers = endorsers
        )

	}
}
