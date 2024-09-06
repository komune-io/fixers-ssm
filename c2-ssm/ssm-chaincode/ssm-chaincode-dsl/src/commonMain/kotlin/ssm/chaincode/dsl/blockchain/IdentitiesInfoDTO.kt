package ssm.chaincode.dsl.blockchain

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

@JsExport
@JsName("IdentitiesInfoDTO")
interface IdentitiesInfoDTO {
	/**
	 * TODO
	 * @example "2e3900b7-76f4-4828-9742-31bd39015de6"
	 */
	val id: String

	/**
	 * TODO
	 * @example "PeerKomune2"
	 */
	val mspid: String
}

/**
 * @d2 model
 * @parent [ssm.chaincode.dsl.SsmChaincodeD2Model]
 */
@Serializable
@JsExport
@JsName("IdentitiesInfo")
class IdentitiesInfo(
	override val id: String,
	override val mspid: String,
) : IdentitiesInfoDTO
