package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrentlyList
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.UserRegisterCommand
import ssm.tx.dsl.features.user.SsmTxUserGrantFunction
import ssm.tx.dsl.features.user.SsmUserGrantCommand
import ssm.tx.dsl.features.user.SsmUserGrantedResult

class SsmUserGrantFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmTxService: SsmTxService,
): SsmTxUserGrantFunction {

	override suspend fun invoke(msgs: Flow<SsmUserGrantCommand>): Flow<SsmUserGrantedResult> = msgs.map { payload ->
		UserRegisterCommand(
			agent = payload.agent,
			chaincodeUri = payload.chaincodeUri.burst(),
			signerName = payload.signerName
		)
	}.chunk(chunking) {
		ssmTxService.sendRegisterUser(it).map { result ->
			SsmUserGrantedResult(
				transactionId = result.transactionId,
			)
		}
	}.flattenConcurrentlyList()

}
