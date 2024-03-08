package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.f2.utils.SsmException
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.user.SsmTxUserGrantFunction
import ssm.tx.dsl.features.user.SsmUserGrantCommand
import ssm.tx.dsl.features.user.SsmUserGrantedResult

class SsmUserGrantFunctionImpl(
	private val ssmTxService: SsmTxService
): SsmTxUserGrantFunction {

	override suspend fun invoke(msg: Flow<SsmUserGrantCommand>): Flow<SsmUserGrantedResult> = msg.map { payload ->
		try {
			ssmTxService.sendRegisterUser(payload.chaincodeUri.burst(), payload.agent, payload.signerName)!!.let { result ->
				SsmUserGrantedResult(
					transactionId = result.transactionId,
				)
			}
		} catch (e: Exception) {
			throw SsmException(e)
		}
	}
}
