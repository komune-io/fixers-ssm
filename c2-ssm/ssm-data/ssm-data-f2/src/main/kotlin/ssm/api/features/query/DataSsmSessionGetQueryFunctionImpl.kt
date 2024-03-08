package ssm.api.features.query

import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.api.features.query.internal.DataSsmSessionConvertFunctionImpl
import ssm.api.features.query.internal.DataSsmSessionConvertQuery
import ssm.chaincode.dsl.model.uri.asChaincodeUri
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQueryDTO
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQueryResult
import ssm.data.dsl.features.query.DataSsmSessionGetQueryResultDTO

class DataSsmSessionGetQueryFunctionImpl(
	private val ssmGetSessionQueryFunction: SsmGetSessionQueryFunction,
	private val dataSsmSessionConvertFunctionImpl: DataSsmSessionConvertFunctionImpl,
) : DataSsmSessionGetQueryFunction {

	override suspend fun invoke(msg: Flow<DataSsmSessionGetQueryDTO>): Flow<DataSsmSessionGetQueryResultDTO> =
		msg.map { payload ->
			try {
				SsmGetSessionQuery(
					chaincodeUri = payload.ssmUri.asChaincodeUri(),
					sessionName = payload.sessionName,
				).invokeWith(ssmGetSessionQueryFunction)
					.item?.let { sessionState ->
						DataSsmSessionConvertQuery(
							sessionState = sessionState,
							ssmUri = payload.ssmUri.burst()
						).invokeWith(dataSsmSessionConvertFunctionImpl)
					}.let {
						DataSsmSessionGetQueryResult(it)
					}
			} catch (e: Exception) {
				e.printStackTrace()
				DataSsmSessionGetQueryResult(null)
			}
		}
}
