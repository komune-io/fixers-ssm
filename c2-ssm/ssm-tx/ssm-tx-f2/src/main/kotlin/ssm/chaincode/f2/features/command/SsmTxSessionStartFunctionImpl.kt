package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.SsmStartCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartResult
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

class SsmTxSessionStartFunctionImpl(
	private val ssmTxService: SsmTxService
): SsmTxSessionStartFunction {

	override suspend fun invoke(msgs: Flow<SsmSessionStartCommand>): Flow<SsmSessionStartResult> = msgs.map { payload ->
		ssmTxService.sendStart(payload.chaincodeUri.burst(), payload.session, payload.signerName).let { result ->
			SsmSessionStartResult(
				transactionId = result.transactionId,
			)
		}
	}


//	override suspend fun invoke(msgs: Flow<SsmSessionStartCommand>): Flow<SsmSessionStartResult> =  msgs.map { payload ->
//		SsmStartCommand(
//			session = payload.session,
//			chaincodeUri = payload.chaincodeUri.burst(),
//			signerName = payload.signerName
//		)
//	}.let {
//		ssmCommandService.sendStart(it.toList()).map { result ->
//			SsmSessionStartResult(
//				transactionId = result.transactionId,
//			)
//		}.asFlow()
//	}
}
