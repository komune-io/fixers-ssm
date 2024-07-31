package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrentlyList
import ssm.chaincode.dsl.model.uri.burst
import ssm.sdk.core.SsmTxService
import ssm.sdk.core.command.UserRegisterCommand
import ssm.tx.dsl.features.user.SsmTxUserRegisterFunction
import ssm.tx.dsl.features.user.SsmUserRegisterCommand
import ssm.tx.dsl.features.user.SsmUserRegisteredResult

class SsmUserRegisterFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmTxService: SsmTxService
): SsmTxUserRegisterFunction {

	override suspend fun invoke(msgs: Flow<SsmUserRegisterCommand>): Flow<SsmUserRegisteredResult> = msgs.map { payload ->
		UserRegisterCommand(
			agent = payload.agent,
			chaincodeUri = payload.chaincodeUri.burst(),
			signerName = payload.signerName
		)
	}.chunk(chunking) {
		ssmTxService.sendRegisterUser(it).map { result ->
			SsmUserRegisteredResult(
				transactionId = result.transactionId,
			)
		}
	}.flattenConcurrentlyList()

}
