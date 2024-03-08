package ssm.chaincode.f2

import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.chaincode.f2.features.command.SsmTxSessionPerformActionFunctionImpl
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.SsmTxUserFunctions
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

class SsmTxUserServiceImpl(
	ssmTxService: SsmTxService,
	private val ssmTxSessionPerformActionFunction: SsmTxSessionPerformActionFunctionImpl =
		SsmTxSessionPerformActionFunctionImpl(ssmTxService),
) : SsmTxUserFunctions {
	override fun ssmTxSessionPerformActionFunction(config: SsmChaincodeConfig): SsmTxSessionPerformActionFunction {
		return ssmTxSessionPerformActionFunction
	}

}
