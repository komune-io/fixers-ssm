package ssm.chaincode.dsl.model.uri

import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import ssm.chaincode.dsl.model.ChaincodeId
import ssm.chaincode.dsl.model.ChannelId
import ssm.chaincode.dsl.model.SsmName

typealias SsmVersion = String

const val DEFAULT_VERSION = "1.0.0"

@Serializable
@JsExport
@JsName("SsmUriDTO")
interface SsmUriDTO {
	val uri: String
}

fun SsmUriDTO.burst() = SsmUri(uri)
fun SsmUriDTO.asChaincodeUri() = SsmUri(uri).chaincodeUri

@Serializable
@JsExport
@JsName("SsmUri")
data class SsmUri(override val uri: String): SsmUriDTO {

	companion object {
		const val PARTS = 4
		const val PREFIX = "ssm"
	}

	private val burst = uri.split(":")

	init {
		require(burst.size == PARTS)
		require(burst.first() == PREFIX)
	}

	@Transient
	val channelId
		get() = burst[1]
	@Transient
	val chaincodeId
		get() = burst[2]
	@Transient
	val ssmName
		get() = burst.get(index = 3)
	@Transient
	val ssmVersion
		get() = DEFAULT_VERSION
	@Transient
	val chaincodeUri
		get() = ChaincodeUri.from(channelId, chaincodeId)

}

fun SsmUri.Companion.from(
		 channelId: ChannelId,
		 chaincodeId: ChaincodeId,
		 ssmName: SsmName,
) = SsmUri("${PREFIX}:$channelId:$chaincodeId:$ssmName")
