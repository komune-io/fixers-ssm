package ssm.chaincode.f2

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
import ssm.chaincode.f2.query.SsmGetAdminFunctionImpl
import ssm.chaincode.f2.query.SsmGetQueryFunctionImpl
import ssm.chaincode.f2.query.SsmGetSessionLogsQueryFunctionImpl
import ssm.chaincode.f2.query.SsmGetSessionQueryFunctionImpl
import ssm.chaincode.f2.query.SsmGetTransactionQueryFunctionImpl
import ssm.chaincode.f2.query.SsmGetUserFunctionImpl
import ssm.chaincode.f2.query.SsmListAdminQueryFunctionImpl
import ssm.chaincode.f2.query.SsmListSessionQueryFunctionImpl
import ssm.chaincode.f2.query.SsmListSsmQueryFunctionImpl
import ssm.chaincode.f2.query.SsmListUserQueryFunctionImpl
import ssm.sdk.core.SsmQueryService
import ssm.sdk.core.SsmSdkConfig
import ssm.sdk.core.SsmServiceFactory

class ChaincodeSsmQueriesImpl(
    config: SsmChaincodeConfig,
    private val ssmQueryService: SsmQueryService
		= SsmServiceFactory.builder(SsmSdkConfig(config.url)).buildQueryService()
): SsmChaincodeQueries {

	override fun ssmGetAdminFunction(): SsmGetAdminFunction {
		return SsmGetAdminFunctionImpl(ssmQueryService)
	}

	override fun ssmGetQueryFunction(): SsmGetQueryFunction {
		return SsmGetQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmGetSessionLogsQueryFunction(): SsmGetSessionLogsQueryFunction {
		return SsmGetSessionLogsQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmGetSessionQueryFunction(): SsmGetSessionQueryFunction {
		return SsmGetSessionQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmGetTransactionQueryFunction(): SsmGetTransactionQueryFunction {
		return SsmGetTransactionQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmGetUserFunction(): SsmGetUserFunction {
		return SsmGetUserFunctionImpl(ssmQueryService)
	}

	override fun ssmListAdminQueryFunction(): SsmListAdminQueryFunction {
		return SsmListAdminQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmListSessionQueryFunction(): SsmListSessionQueryFunction {
		return SsmListSessionQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmListSsmQueryFunction(): SsmListSsmQueryFunction {
		return SsmListSsmQueryFunctionImpl(ssmQueryService)
	}

	override fun ssmListUserQueryFunction(): SsmListUserQueryFunction {
		return SsmListUserQueryFunctionImpl(ssmQueryService)
	}
}
