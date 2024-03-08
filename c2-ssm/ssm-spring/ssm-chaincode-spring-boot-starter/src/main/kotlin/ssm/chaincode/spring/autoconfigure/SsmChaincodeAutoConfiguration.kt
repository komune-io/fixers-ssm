package ssm.chaincode.spring.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ssm.chaincode.dsl.SsmChaincodeQueries
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.chaincode.dsl.query.SsmGetAdminFunction
import ssm.chaincode.dsl.query.SsmGetQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.chaincode.dsl.query.SsmGetTransactionQueryFunction
import ssm.chaincode.dsl.query.SsmGetUserFunction
import ssm.chaincode.dsl.query.SsmListAdminQueryFunction
import ssm.chaincode.dsl.query.SsmListSessionQueryFunction
import ssm.chaincode.dsl.query.SsmListSsmQueryFunction
import ssm.chaincode.dsl.query.SsmListUserQueryFunction
import ssm.chaincode.f2.ChaincodeSsmQueriesImpl

@ConditionalOnProperty(prefix = "ssm.chaincode", name = ["url"])
@EnableConfigurationProperties(SsmChaincodeProperties::class)
@Configuration(proxyBeanMethods = false)
class SsmChaincodeAutoConfiguration {

	@Bean
	fun ssmChaincodeConfig(
		ssmChaincodeProperties: SsmChaincodeProperties
	): SsmChaincodeConfig = ssmChaincodeProperties.chaincode

	@Bean
	fun ssmChaincodeQueryFunctions(
		ssmChaincodeConfig: SsmChaincodeConfig
	): ChaincodeSsmQueriesImpl = ChaincodeSsmQueriesImpl(ssmChaincodeConfig)
}

@ConditionalOnBean(ChaincodeSsmQueriesImpl::class)
@Configuration(proxyBeanMethods = false)
class SsmChaincodeF2AutoConfiguration(
	private val functions: ChaincodeSsmQueriesImpl
) : SsmChaincodeQueries {

	@Bean
	override fun ssmGetAdminFunction(): SsmGetAdminFunction {
		return functions.ssmGetAdminFunction()
	}

	@Bean
	override fun ssmGetQueryFunction(): SsmGetQueryFunction {
		return functions.ssmGetQueryFunction()
	}

	@Bean
	override fun ssmGetSessionLogsQueryFunction(): SsmGetSessionLogsQueryFunction {
		return functions.ssmGetSessionLogsQueryFunction()
	}

	@Bean
	override fun ssmGetSessionQueryFunction(): SsmGetSessionQueryFunction {
		return functions.ssmGetSessionQueryFunction()
	}

	@Bean
	override fun ssmGetTransactionQueryFunction(): SsmGetTransactionQueryFunction {
		return functions.ssmGetTransactionQueryFunction()
	}

	@Bean
	override fun ssmGetUserFunction(): SsmGetUserFunction {
		return functions.ssmGetUserFunction()
	}

	@Bean
	override fun ssmListAdminQueryFunction(): SsmListAdminQueryFunction {
		return functions.ssmListAdminQueryFunction()
	}

	@Bean
	override fun ssmListSessionQueryFunction(): SsmListSessionQueryFunction {
		return functions.ssmListSessionQueryFunction()
	}

	@Bean
	override fun ssmListSsmQueryFunction(): SsmListSsmQueryFunction {
		return functions.ssmListSsmQueryFunction()
	}

	@Bean
	override fun ssmListUserQueryFunction(): SsmListUserQueryFunction {
		return functions.ssmListUserQueryFunction()
	}
}
