package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrently
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.f2.utils.SsmException
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.UserRegisterCommand
import ssm.tx.dsl.features.ssm.SsmCreateCommand
import ssm.tx.dsl.features.ssm.SsmCreateResult
import ssm.tx.dsl.features.ssm.SsmTxCreateFunction
import ssm.tx.dsl.features.user.SsmUserGrantCommand
import ssm.tx.dsl.features.user.SsmUserGrantedResult

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
	}.flattenConcurrently()
}
