package ssm.chaincode.dsl.config

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

/**
 * The configuration needed for
 * @d2 model
 * @example "ssm"
 * @parent [ssm.chaincode.dsl.SsmChaincodeD2Model]
 */
@JsExport
@JsName("SsmChaincodePropertiesDTO")
interface SsmChaincodePropertiesDTO {
	/**
	 * URL of the peer hosting the chaincode
	 * @example "http://peer.sandbox.Komune.io:9000"
	 */
	val url: String
	val batch: BatchProperties
}

@Serializable
@JsExport
@JsName("ChaincodeSsmConfig")
class SsmChaincodeConfig(
	override val url: String,
	override val batch: BatchProperties = BatchProperties()
) : SsmChaincodePropertiesDTO
