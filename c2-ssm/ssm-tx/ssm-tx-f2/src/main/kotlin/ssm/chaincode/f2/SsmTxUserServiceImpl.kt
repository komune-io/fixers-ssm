package ssm.chaincode.f2

import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.chaincode.f2.features.command.SsmTxSessionPerformActionFunctionImpl
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.SsmTxUserFunctions
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

class SsmTxUserServiceImpl(
	private val ssmTxService: SsmTxService,
	private val chunking: InvokeChunkedProps
) : SsmTxUserFunctions {
	override fun ssmTxSessionPerformActionFunction(): SsmTxSessionPerformActionFunction {
		return SsmTxSessionPerformActionFunctionImpl(chunking, ssmTxService)
	}
}
