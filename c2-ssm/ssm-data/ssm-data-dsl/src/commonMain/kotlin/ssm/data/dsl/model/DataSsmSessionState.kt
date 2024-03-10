package ssm.data.dsl.model

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import ssm.chaincode.dsl.blockchain.Transaction
import ssm.chaincode.dsl.blockchain.TransactionDTO
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.model.SsmSessionStateDTO

@Serializable
@JsExport
@JsName("DataSsmSessionStateDTO")
interface DataSsmSessionStateDTO {
	/**
	 * The state of an SSM session
	 */
	val details: SsmSessionStateDTO

	/**
	 * Transaction that initiated the state of the session
	 */
	val transaction: TransactionDTO?
}

/**
 * @d2 model
 * @parent [ssm.data.dsl.DataSsmD2Model]
 * Represents an SSM session state with metadata
 */
@Serializable
@JsExport
@JsName("DataSsmSessionState")
class DataSsmSessionState(
	override val details: SsmSessionState,
	override val transaction: Transaction?,
) : DataSsmSessionStateDTO
