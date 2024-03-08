package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.ssm.SsmSessionStartCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartResult
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

class SsmTxSessionStartFunctionImpl(
	private val ssmTxService: SsmTxService
): SsmTxSessionStartFunction {

	override suspend fun invoke(msg: Flow<SsmSessionStartCommand>): Flow<SsmSessionStartResult> = msg.map { payload ->
		ssmTxService.sendStart(payload.chaincodeUri.burst(), payload.session, payload.signerName)!!.let { result ->
			SsmSessionStartResult(
				transactionId = result.transactionId,
			)
		}
	}
}
