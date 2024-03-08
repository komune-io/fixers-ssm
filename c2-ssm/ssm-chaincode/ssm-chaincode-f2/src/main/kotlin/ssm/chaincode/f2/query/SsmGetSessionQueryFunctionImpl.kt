package ssm.chaincode.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionResult
import ssm.sdk.core.SsmQueryService

class SsmGetSessionQueryFunctionImpl(
	private val queryService: SsmQueryService
): SsmGetSessionQueryFunction {

	override suspend fun invoke(msg: Flow<SsmGetSessionQuery>): Flow<SsmGetSessionResult> = msg.map { payload ->
		queryService.getSession(payload.chaincodeUri, payload.sessionName).let(::SsmGetSessionResult)
	}
}
