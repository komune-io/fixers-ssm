package ssm.chaincode.dsl.query

import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import ssm.chaincode.dsl.SsmItemResultDTO
import ssm.chaincode.dsl.SsmQueryDTO
import ssm.chaincode.dsl.blockchain.Transaction
import ssm.chaincode.dsl.blockchain.TransactionId
import ssm.chaincode.dsl.model.uri.ChaincodeUri

/**
 * Retrieves an Transaction
 * @d2 function
 * @parent [ssm.chaincode.dsl.SsmChaincodeD2Query]
 * @title Get a transaction
 */
typealias SsmGetTransactionQueryFunction = F2Function<SsmGetTransactionQuery, SsmGetTransactionQueryResult>

/**
 * @d2 query
 * @parent [SsmGetTransactionQueryFunction]
 * @title Get a transaction: Parameters
 */
@Serializable
@JsExport
@JsName("SsmGetTransactionQuery")
data class SsmGetTransactionQuery(
	override val chaincodeUri: ChaincodeUri,
	val id: TransactionId,
) : SsmQueryDTO

/**
 * @d2 event
 * @parent [SsmGetTransactionQueryFunction]
 * @title Get a transaction: Result
 */
@Serializable
@JsExport
@JsName("SsmGetTransactionQueryResult")
class SsmGetTransactionQueryResult(
	override val item: Transaction?,
) : SsmItemResultDTO<Transaction>
