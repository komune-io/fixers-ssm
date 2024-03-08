package ssm.chaincode.dsl.blockchain

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

@Serializable
@JsExport
@JsName("EnvelopeType")
enum class EnvelopeType {
	TRANSACTION_ENVELOPE, ENVELOPE
}
