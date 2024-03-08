package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.ssm.SsmSessionPerformActionCommand
import ssm.tx.dsl.features.ssm.SsmSessionPerformActionResult
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

class SsmTxSessionPerformActionFunctionImpl(
	private val ssmTxService: SsmTxService
) : SsmTxSessionPerformActionFunction {

	override suspend fun invoke(
		msg: Flow<SsmSessionPerformActionCommand>
	): Flow<SsmSessionPerformActionResult> = msg.map { payload ->
		ssmTxService.sendPerform(
			payload.chaincodeUri.burst(), payload.action, payload.context, payload.signerName
		).let { result ->
			SsmSessionPerformActionResult(result.transactionId)
		}
	}
}
