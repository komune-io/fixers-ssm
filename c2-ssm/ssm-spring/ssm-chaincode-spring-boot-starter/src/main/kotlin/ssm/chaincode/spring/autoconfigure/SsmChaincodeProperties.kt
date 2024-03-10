package ssm.chaincode.spring.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import ssm.chaincode.dsl.config.SsmChaincodeConfig

@ConfigurationProperties(prefix = "ssm")
data class SsmChaincodeProperties(
	val chaincode: SsmChaincodeConfig
)
