package ssm.api.features.query

import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.api.features.query.internal.DataSsmSessionConvertFunctionImpl
import ssm.api.features.query.internal.DataSsmSessionConvertQuery
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.burst
import ssm.chaincode.dsl.model.uri.from
import ssm.couchdb.dsl.query.CouchdbSsmSessionStateListQuery
import ssm.couchdb.dsl.query.CouchdbSsmSessionStateListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionListQueryDTO
import ssm.data.dsl.features.query.DataSsmSessionListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionListQueryResult
import ssm.data.dsl.features.query.DataSsmSessionListQueryResultDTO

class DataSsmSessionListQueryFunctionImpl(
	private val dataSsmSessionConvertFunctionImpl: DataSsmSessionConvertFunctionImpl,
	private val couchdbSsmSessionStateListQueryFunction: CouchdbSsmSessionStateListQueryFunction,
) : DataSsmSessionListQueryFunction {

	override suspend fun invoke(msg: Flow<DataSsmSessionListQueryDTO>): Flow<DataSsmSessionListQueryResultDTO> =
		msg.map { payload ->
			CouchdbSsmSessionStateListQuery(
				chaincodeUri = ChaincodeUri.from(
					channelId = payload.ssmUri.burst().channelId,
					chaincodeId = payload.ssmUri.burst().chaincodeId,
				),
				ssm = payload.ssmUri.burst().ssmName,
				pagination = null
			).invokeWith(couchdbSsmSessionStateListQueryFunction)
				.items
				.filter { sessionState -> sessionState.session.isNotBlank() }
				.map { sessionState ->
					DataSsmSessionConvertQuery(
						sessionState = sessionState as SsmSessionState,
						ssmUri = payload.ssmUri.burst()
					).invokeWith(dataSsmSessionConvertFunctionImpl)
				}.let {
					DataSsmSessionListQueryResult(it)
				}
		}
}
