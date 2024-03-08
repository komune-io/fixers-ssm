package ssm.couchdb.dsl.query

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentDTO
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.ChaincodeUriDTO

/**
 * @title Fetch all admins
 * @d2 function
 * @order 20
 * @parent [ssm.couchdb.dsl.CouchdbSsmD2Query]
 */
typealias CouchdbAdminListQueryFunction = F2Function<CouchdbAdminListQueryDTO, CouchdbAdminListQueryResultDTO>

/**
 * @title Get all chaincode: Parameters
 * @d2 model
 * @parent [CouchdbAdminListQueryFunction]
 */
@Serializable
@JsExport
@JsName("CouchdbAdminListQueryDTO")
interface CouchdbAdminListQueryDTO : Query {
	/**
	 * The unique id of a chaincode.
	 */
	val chaincodeUri: ChaincodeUriDTO
}

/**
 * @d2 model
 * @title Get all admins: Result
 * @parent [CouchdbAdminListQueryFunction]
 */
@Serializable
@JsExport
@JsName("CouchdbAdminListQueryResultDTO")
interface CouchdbAdminListQueryResultDTO : Event {
	/**
	 * Names of the admin.
	 */
	val items: List<AgentDTO>
}

@Serializable
@JsExport
@JsName("CouchdbAdminListQuery")
class CouchdbAdminListQuery(
	override val chaincodeUri: ChaincodeUri
) : CouchdbAdminListQueryDTO

@Serializable
@JsExport
@JsName("CouchdbAdminListQueryResult")
class CouchdbAdminListQueryResult(
	override val items: List<Agent>,
) : CouchdbAdminListQueryResultDTO
