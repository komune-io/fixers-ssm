package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.f2.utils.SsmException
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.ssm.SsmCreateCommand
import ssm.tx.dsl.features.ssm.SsmCreateResult
import ssm.tx.dsl.features.ssm.SsmTxCreateFunction

class SsmTxCreateFunctionImpl(
	private val ssmTxService: SsmTxService
): SsmTxCreateFunction {

	override suspend fun invoke(msg: Flow<SsmCreateCommand>): Flow<SsmCreateResult> = msg.map { payload ->
		try {
			ssmTxService.sendCreate(payload.chaincodeUri.burst(), payload.ssm, payload.signerName).let { invokeReturn ->
				SsmCreateResult(
					transactionId = invokeReturn!!.transactionId,
				)
			}
		} catch (e: Exception) {
			throw SsmException(e)
		}
	}
}
