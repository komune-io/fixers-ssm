package ssm.tx.config.spring.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.sdk.sign.model.SignerAdmin

@ConfigurationProperties(prefix = "ssm")
data class SsmTxProperties(
	val chaincode: SsmChaincodeConfig?,
	val signer: SignerFileConfig?,
) {
	class SignerFileConfig(
		val admin: SignerAgentFileConfig?,
		val user: SignerAgentFileConfig?,
	)
	class SignerAgentFileConfig(
		val name: String,
		val key: String,
	)
}

fun SsmTxProperties.SignerAgentFileConfig.signer(): SignerAdmin {
	return SignerAdmin.loadFromFile(name, key)
}
