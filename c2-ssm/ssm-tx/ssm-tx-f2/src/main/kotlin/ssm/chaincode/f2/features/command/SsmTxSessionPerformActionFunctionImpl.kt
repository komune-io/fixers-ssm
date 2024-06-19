package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrently
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.SsmPerformCommand
import ssm.tx.dsl.features.ssm.SsmSessionPerformActionCommand
import ssm.tx.dsl.features.ssm.SsmSessionPerformActionResult
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

class SsmTxSessionPerformActionFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmTxService: SsmTxService
) : SsmTxSessionPerformActionFunction {

	override suspend fun invoke(
		msgs: Flow<SsmSessionPerformActionCommand>
	): Flow<SsmSessionPerformActionResult> = msgs.map { payload ->
		SsmPerformCommand(
			action = payload.action,
			context = payload.context,
			chaincodeUri = payload.chaincodeUri.burst(),
			signerName = payload.signerName
		)
	}.chunk(chunking) {
		ssmTxService.sendPerform(it).map { result ->
			SsmSessionPerformActionResult(
				transactionId = result.transactionId,
			)
		}
	}.flattenConcurrently()
}
