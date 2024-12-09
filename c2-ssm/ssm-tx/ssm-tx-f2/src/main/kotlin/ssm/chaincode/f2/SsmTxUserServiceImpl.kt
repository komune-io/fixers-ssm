package ssm.chaincode.f2

import ssm.chaincode.f2.features.command.SsmTxSessionPerformActionFunctionImpl
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.SsmTxUserFunctions
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

class SsmTxUserServiceImpl(
	private val ssmTxService: SsmTxService,
) : SsmTxUserFunctions {
	override fun ssmTxSessionPerformActionFunction(): SsmTxSessionPerformActionFunction {
		return SsmTxSessionPerformActionFunctionImpl(ssmTxService)
	}
}
