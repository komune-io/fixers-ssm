package ssm.data.dsl.features.query

import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import ssm.chaincode.dsl.model.uri.ChaincodeUri

/**
 * Retrieves all known SSMs
 * @d2 function
 * @parent [ssm.data.dsl.DataSsmD2Query]
 * @order 20
 * @title List SSMs
 */
typealias DataChaincodeListQueryFunction = F2Function<DataChaincodeListQuery, DataChaincodeListQueryResult>

@Serializable
@JsExport
@JsName("DataChaincodeListQueryDTO")
interface DataChaincodeListQueryDTO

/**
 * @d2 query
 * @parent [DataChaincodeListQueryFunction]
 * @title List SSMs: Parameters
 */
@Serializable
@JsExport
@JsName("DataChaincodeListQuery")
class DataChaincodeListQuery : DataChaincodeListQueryDTO

@Serializable
@JsExport
@JsName("DataChaincodeListQueryResultDTO")
interface DataChaincodeListQueryResultDTO {
	/**
	 * List of all retrieved SSMs
	 */
	val items: List<ChaincodeUri>
}

/**
 * @d2 event
 * @parent [DataChaincodeListQueryFunction]
 * @title List SSMs: Result
 */
@Serializable
@JsExport
@JsName("DataChaincodeListQueryResult")
class DataChaincodeListQueryResult(
	override val items: List<ChaincodeUri>,
) : DataChaincodeListQueryResultDTO
