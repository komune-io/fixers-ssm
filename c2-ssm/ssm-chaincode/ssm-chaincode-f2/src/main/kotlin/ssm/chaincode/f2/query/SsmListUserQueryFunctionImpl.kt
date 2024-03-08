package ssm.chaincode.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.query.SsmListUserQuery
import ssm.chaincode.dsl.query.SsmListUserQueryFunction
import ssm.chaincode.dsl.query.SsmListUserResult
import ssm.sdk.core.SsmQueryService

class SsmListUserQueryFunctionImpl(
	private val queryService: SsmQueryService
): SsmListUserQueryFunction {


	override suspend fun invoke(msg: Flow<SsmListUserQuery>): Flow<SsmListUserResult> = msg.map { payload ->
		queryService.listUsers(payload.chaincodeUri).let { items ->
			SsmListUserResult(items.toTypedArray())
		}
	}
}
