package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrently
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.SsmStartCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartResult
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

class SsmTxSessionStartFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmTxService: SsmTxService
): SsmTxSessionStartFunction {

	override suspend fun invoke(msgs: Flow<SsmSessionStartCommand>): Flow<SsmSessionStartResult> = msgs.map { payload ->
		SsmStartCommand(
			session = payload.session,
			chaincodeUri = payload.chaincodeUri.burst(),
			signerName = payload.signerName
		)
	}.chunk(chunking) {
		ssmTxService.sendStart(it).map { result ->
			SsmSessionStartResult(
				transactionId = result.transactionId,
			)
		}
	}.flattenConcurrently()
}
