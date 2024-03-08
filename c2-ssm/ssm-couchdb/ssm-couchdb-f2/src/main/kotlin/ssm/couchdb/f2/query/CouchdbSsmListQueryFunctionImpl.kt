package ssm.couchdb.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.couchdb.client.CouchdbSsmClient
import ssm.couchdb.dsl.model.DocType
import ssm.couchdb.dsl.query.CouchdbSsmListQuery
import ssm.couchdb.dsl.query.CouchdbSsmListQueryFunction
import ssm.couchdb.dsl.query.CouchdbSsmListQueryResult
import ssm.couchdb.f2.commons.chainCodeDbName

class CouchdbSsmListQueryFunctionImpl(
	private val couchdbClient: CouchdbSsmClient,
) : CouchdbSsmListQueryFunction {

	override suspend fun invoke(msg: Flow<CouchdbSsmListQuery>): Flow<CouchdbSsmListQueryResult> = msg.map { payload ->
		couchdbClient
			.fetchAllByDocType(chainCodeDbName(payload.channelId, payload.chaincodeId), DocType.Ssm)
			.let{ list ->
				CouchdbSsmListQueryResult(
					items = list,
					total = list.size,
					pagination = payload.pagination
				)
			}
	}
}
