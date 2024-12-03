package ssm.chaincode.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.query.SsmGetSessionLogsQuery
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryFunction
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryResult
import ssm.sdk.core.GetLogQuery
import ssm.sdk.core.SsmQueryService

class SsmGetSessionLogsQueryFunctionImpl(
	private val queryService: SsmQueryService,
	private val props: InvokeChunkedProps
): SsmGetSessionLogsQueryFunction  {

	// TODO CHANGE THAT should better use flow
	override suspend fun invoke(
		msgs: Flow<SsmGetSessionLogsQuery>
	): Flow<SsmGetSessionLogsQueryResult> = msgs.map { payload ->
		GetLogQuery(
			chaincodeUri = payload.chaincodeUri,
			sessionName = payload.sessionName
		)
	}.chunk(props).map { queries -> queryService.getLogs(queries)}.flatMapConcat { logs ->
		logs.associateBy { it.first().state }.map { (state, logs) ->
			SsmGetSessionLogsQueryResult(
				ssmName = state.ssm!!,
				sessionName = state.session,
				logs = logs
			)
		}.asFlow()
	}
}
