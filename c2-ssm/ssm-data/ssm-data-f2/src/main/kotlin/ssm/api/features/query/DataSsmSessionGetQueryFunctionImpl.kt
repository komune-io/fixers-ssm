package ssm.api.features.query

import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import ssm.api.features.query.internal.DataSsmSessionConvertFunctionImpl
import ssm.api.features.query.internal.DataSsmSessionConvertQuery
import ssm.chaincode.dsl.config.InvokeChunkedProps
import ssm.chaincode.dsl.config.chunk
import ssm.chaincode.dsl.config.flattenConcurrentlyFlow
import ssm.chaincode.dsl.config.groupBy
import ssm.chaincode.dsl.model.uri.SsmUri
import ssm.chaincode.dsl.model.uri.asChaincodeUri
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQueryDTO
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQueryResult
import ssm.data.dsl.features.query.DataSsmSessionGetQueryResultDTO

class DataSsmSessionGetQueryFunctionImpl(
	private val chunking: InvokeChunkedProps,
	private val ssmGetSessionQueryFunction: SsmGetSessionQueryFunction,
	private val dataSsmSessionConvertFunction: DataSsmSessionConvertFunctionImpl,
) : DataSsmSessionGetQueryFunction {

	override suspend fun invoke(msgs: Flow<DataSsmSessionGetQueryDTO>): Flow<DataSsmSessionGetQueryResultDTO> {
		return msgs.groupBy { it.ssmUri }.map { (ssmUri, queries) ->
			queries.map { payload ->
				SsmGetSessionQuery(
					chaincodeUri = ssmUri.asChaincodeUri(),
					sessionName = payload.sessionName,
				)
			}.chunk(chunking).map {
				ssmGetSessionQueryFunction.invoke(it.asFlow())
			}.flattenConcurrentlyFlow().map { result ->
				result.item?.let { item ->
					DataSsmSessionConvertQuery(
						sessionState = item,
						ssmUri = ssmUri as SsmUri
					)
				}
			}.map {
				it?.invokeWith(dataSsmSessionConvertFunction)
			}.map {
				DataSsmSessionGetQueryResult(it)
			}
		}.flattenConcurrentlyFlow()
	}
}
