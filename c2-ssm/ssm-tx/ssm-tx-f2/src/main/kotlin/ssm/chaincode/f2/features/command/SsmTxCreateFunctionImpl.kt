package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrentlyList
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.ssm.SsmCreateCommand
import ssm.tx.dsl.features.ssm.SsmCreateResult
import ssm.tx.dsl.features.ssm.SsmTxCreateFunction

class SsmTxCreateFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmTxService: SsmTxService,
): SsmTxCreateFunction {

	override suspend fun invoke(msgs: Flow<SsmCreateCommand>): Flow<SsmCreateResult> = msgs.map { payload ->
		ssm.sdk.core.command.SsmCreateCommand(
			ssm = payload.ssm,
			chaincodeUri = payload.chaincodeUri.burst(),
			signerName = payload.signerName
		)
	}.chunk(chunking) {
		ssmTxService.sendCreate(it).map { result ->
			SsmCreateResult(
				transactionId = result.transactionId,
			)
		}
	}.flattenConcurrentlyList()
}
