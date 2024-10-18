package ssm.chaincode.dsl

import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import kotlin.js.JsExport
import kotlin.js.JsName
import ssm.chaincode.dsl.model.uri.ChaincodeUri

@JsExport
@JsName("SsmQueryDTO")
interface SsmQueryDTO : Query {
	/**
	 * Uri of the chaincode
	 * @example "chaincode:sandbox:thessm"
	 */
	val chaincodeUri: ChaincodeUri
}

@JsExport
@JsName("SsmItemResultDTO")
interface SsmItemResultDTO<T> : Event {
	val item: T?
}

@JsExport
@JsName("SsmItemsResultDTO")
interface SsmItemsResultDTO<T> : Event {
	val items: Array<T>
}


