package ssm.tx.config.spring.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.sdk.core.SsmQueryService
import ssm.sdk.core.SsmSdkConfig
import ssm.sdk.core.SsmServiceFactory
import ssm.sdk.core.SsmTxService
import ssm.sdk.sign.SsmCmdSigner
import ssm.sdk.sign.SsmCmdSignerSha256RSASigner

@EnableConfigurationProperties(SsmTxProperties::class)
@Configuration(proxyBeanMethods = false)
class SsmTxAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "ssm.chaincode", name = ["url"])
	@ConditionalOnMissingBean(SsmChaincodeConfig::class)
	fun chaincodeSsmConfig(ssmTxCreateProperties: SsmTxProperties): SsmChaincodeConfig =
		ssmTxCreateProperties.chaincode!!

	@Bean
	@ConditionalOnMissingBean(SsmCmdSignerSha256RSASigner::class)
	fun ssmCmdSigner(ssmTxCreateProperties: SsmTxProperties): SsmCmdSigner {
		return SsmCmdSignerSha256RSASigner(*listOfNotNull(
			ssmTxCreateProperties.signer?.admin?.signer(),
			ssmTxCreateProperties.signer?.user?.signer()
		).toTypedArray())
	}

	@Bean
	@ConditionalOnBean(value = [SsmCmdSigner::class, SsmChaincodeConfig::class])
	@ConditionalOnMissingBean(SsmTxService::class)
	fun ssmTxService(ssmCmdSigner: SsmCmdSigner, ssmChaincodeConfig: SsmChaincodeConfig): SsmTxService {
		return SsmServiceFactory.builder(
			SsmSdkConfig(ssmChaincodeConfig.url)
		).buildTxService(ssmCmdSigner)
	}

	@Bean
	@ConditionalOnBean(value = [SsmChaincodeConfig::class])
	@ConditionalOnMissingBean(SsmQueryService::class)
	fun ssmQueryService(ssmChaincodeConfig: SsmChaincodeConfig): SsmQueryService {
		return SsmServiceFactory.builder(
			SsmSdkConfig(ssmChaincodeConfig.url)
		).buildQueryService()
	}
}
