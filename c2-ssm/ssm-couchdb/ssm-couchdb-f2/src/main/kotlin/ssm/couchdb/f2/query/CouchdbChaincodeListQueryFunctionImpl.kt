package ssm.couchdb.f2.query

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.from
import ssm.couchdb.client.CouchdbSsmClient
import ssm.couchdb.dsl.query.CouchdbChaincodeListQueryDTO
import ssm.couchdb.dsl.query.CouchdbChaincodeListQueryFunction
import ssm.couchdb.dsl.query.CouchdbChaincodeListQueryResult
import ssm.couchdb.dsl.query.CouchdbChaincodeListQueryResultDTO

class CouchdbChaincodeListQueryFunctionImpl(
	private val couchdbClient: CouchdbSsmClient,
) : CouchdbChaincodeListQueryFunction {
	companion object {
		const val DB_LSCC = "_lscc"
	}

	override suspend fun invoke(
		msg: Flow<CouchdbChaincodeListQueryDTO>
	): Flow<CouchdbChaincodeListQueryResultDTO> = msg.map { _ ->
		couchdbClient.cloudant.allDbs.execute()
			.result
			.asFlow()
			.filter { it.contains(DB_LSCC) }
			.flatMapMerge { dbName ->
				val channelId = dbName.removeSuffix(DB_LSCC)
				couchdbClient.fetchAll(dbName).map { document ->
					document.id
				}.map { chaincodeId ->
					ChaincodeUri.from(
						channelId = channelId,
						chaincodeId = chaincodeId
					)
				}.asFlow()
			}.toList()
			.let {
				CouchdbChaincodeListQueryResult(
					items = it
				)
			}

	}

}
