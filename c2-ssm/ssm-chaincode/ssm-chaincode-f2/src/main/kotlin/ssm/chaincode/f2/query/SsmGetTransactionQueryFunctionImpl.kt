package ssm.chaincode.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.query.SsmGetTransactionQuery
import ssm.chaincode.dsl.query.SsmGetTransactionQueryFunction
import ssm.chaincode.dsl.query.SsmGetTransactionQueryResult
import ssm.sdk.core.SsmQueryService

class SsmGetTransactionQueryFunctionImpl(
	private val queryService: SsmQueryService
) : SsmGetTransactionQueryFunction {

	override suspend fun invoke(msg: Flow<SsmGetTransactionQuery>): Flow<SsmGetTransactionQueryResult> =
		msg.map { payload ->
			queryService.getTransaction(payload.chaincodeUri, payload.id)
				.let(::SsmGetTransactionQueryResult)
		}
}
