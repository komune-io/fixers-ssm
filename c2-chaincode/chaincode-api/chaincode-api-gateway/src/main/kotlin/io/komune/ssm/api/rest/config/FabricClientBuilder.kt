package io.komune.ssm.api.rest.config

import io.komune.ssm.api.fabric.FabricChainCodeClient
import io.komune.ssm.api.fabric.FabricChannelClient
import io.komune.ssm.api.fabric.FabricUserClient
import io.komune.ssm.api.fabric.config.FabricConfig
import io.komune.ssm.api.fabric.factory.FabricChannelFactory
import io.komune.ssm.api.fabric.factory.FabricClientFactory
import io.komune.ssm.api.rest.config.ChannelChaincode
import io.komune.ssm.api.rest.config.ChannelId

class FabricClientBuilder(val coopConfig: HeraclesConfigProps) {

	fun getChannelConfig(channelId: ChannelId): ChannelChaincode {
		return coopConfig.getChannelChaincodes().get(channelId)
			?: throw ChannelConfigNotFoundException(channelId)
	}

	fun getFabricConfig(channelId: ChannelId): FabricConfig {
		val channelConfig = getChannelConfig(channelId)
		return FabricConfig.loadFromFile(channelConfig.config.file)
	}

	fun getFabricClientFactory(channelId: ChannelId): FabricClientFactory {
		val channelConfig = getChannelConfig(channelId)
		val fabricConfig = getFabricConfig(channelId)
		return FabricClientFactory.factory(fabricConfig, channelConfig.config.crypto)
	}

	fun getFabricChannelFactory(channelId: ChannelId): FabricChannelFactory {
		val channelConfig = getChannelConfig(channelId)
		val fabricConfig = getFabricConfig(channelId)
		return FabricChannelFactory.factory(fabricConfig, channelConfig.config.crypto)
	}

	fun getFabricChainCodeClient(channelId: ChannelId): FabricChainCodeClient {
		val fabricChannelFactory = getFabricChannelFactory(channelId)
		return FabricChainCodeClient(fabricChannelFactory)
	}

	fun getFabricChannelClient(channelId: ChannelId): FabricChannelClient {
		val fabricChannelFactory = getFabricChannelFactory(channelId)
		return FabricChannelClient(fabricChannelFactory)
	}

	fun getFabricUserClient(channelId: ChannelId): FabricUserClient {
		val fabricConfig = getFabricConfig(channelId)
		val fabricClientFactory = getFabricClientFactory(channelId)
		return FabricUserClient(fabricConfig, fabricClientFactory)
	}

}