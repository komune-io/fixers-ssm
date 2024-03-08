package ssm.chaincode.f2.features.command

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.f2.utils.SsmException
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.features.user.SsmTxUserRegisterFunction
import ssm.tx.dsl.features.user.SsmUserRegisterCommand
import ssm.tx.dsl.features.user.SsmUserRegisteredResult

class SsmUserRegisterFunctionImpl(
	private val ssmTxService: SsmTxService
): SsmTxUserRegisterFunction {

	override suspend fun invoke(msg: Flow<SsmUserRegisterCommand>): Flow<SsmUserRegisteredResult> = msg.map { payload ->
		try {
			ssmTxService.sendRegisterUser(payload.chaincodeUri.burst(), payload.agent, payload.signerName)!!.let { result ->
				SsmUserRegisteredResult(
					transactionId = result.transactionId
				)
			}
		} catch (e: Exception) {
			throw SsmException(e)
		}
	}
}
