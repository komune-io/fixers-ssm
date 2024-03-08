package ssm.chaincode.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.query.SsmGetSessionLogsQuery
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryResult
import ssm.sdk.core.SsmQueryService

class SsmGetSessionLogsQueryFunctionImpl(
	private val queryService: SsmQueryService
): SsmGetSessionLogsQueryFunction  {

	override suspend fun invoke(
		msg: Flow<SsmGetSessionLogsQuery>
	): Flow<SsmGetSessionLogsQueryResult> = msg.map { payload ->
		queryService.log(payload.chaincodeUri, payload.sessionName)
			.let { logs ->
				SsmGetSessionLogsQueryResult(
					ssmName = payload.ssmName,
					sessionName = payload.sessionName,
					logs = logs
				)
			}
	}
}
