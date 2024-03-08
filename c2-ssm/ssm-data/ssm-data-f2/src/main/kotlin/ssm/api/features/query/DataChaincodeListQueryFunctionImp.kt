package ssm.api.features.query

import f2.dsl.fnc.invoke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.chaincode.dsl.model.uri.burst
import ssm.couchdb.dsl.query.CouchdbChaincodeListQuery
import ssm.couchdb.dsl.query.CouchdbChaincodeListQueryFunction
import ssm.data.dsl.features.query.DataChaincodeListQuery
import ssm.data.dsl.features.query.DataChaincodeListQueryFunction
import ssm.data.dsl.features.query.DataChaincodeListQueryResult

class DataChaincodeListQueryFunctionImp(
	private val couchdbChaincodeListQueryFunction: CouchdbChaincodeListQueryFunction
): DataChaincodeListQueryFunction {

	override suspend fun invoke(msg: Flow<DataChaincodeListQuery>): Flow<DataChaincodeListQueryResult> =
		msg.map {
			couchdbChaincodeListQueryFunction.invoke(CouchdbChaincodeListQuery()).let {
				DataChaincodeListQueryResult(it.items.map {it.burst()})
			}
		}
}
