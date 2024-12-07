package ssm.chaincode.f2.query

import f2.dsl.fnc.operators.flattenConcurrently
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.model.SsmName
import ssm.chaincode.dsl.query.SsmGetSessionLogsQuery
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryResult
import ssm.sdk.core.GetLogQuery
import ssm.sdk.core.SsmQueryService

class SsmGetSessionLogsQueryFunctionImpl(
	private val queryService: SsmQueryService,
	private val props: InvokeChunkedProps
): SsmGetSessionLogsQueryFunction  {

	override suspend fun invoke(
		msgs: Flow<SsmGetSessionLogsQuery>
	): Flow<SsmGetSessionLogsQueryResult> = msgs.map { payload ->
		GetLogQueryWithSsmName(
			ssmName = payload.ssmName,
			getLogQuery = GetLogQuery(
				chaincodeUri = payload.chaincodeUri,
				sessionName = payload.sessionName
			)
		)
	}.chunk(props).map { queries ->
		val logsResult = queryService.getLogs(queries.map { it.getLogQuery })
		val logBySessionName = logsResult.associateBy { log ->
			log.firstOrNull()?.state?.session
		}
		queries.map { query ->
			val logs = logBySessionName[query.getLogQuery.sessionName] ?: emptyList()
			SsmGetSessionLogsQueryResult(
				ssmName = query.ssmName,
				sessionName = query.getLogQuery.sessionName,
				logs = logs
			)
		}.asFlow()
	}.flattenConcurrently()
}

class GetLogQueryWithSsmName(
	val ssmName: SsmName,
	val getLogQuery: GetLogQuery,
)
