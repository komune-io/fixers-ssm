package x2.api.ssm.model.features

import ssm.dsl.SsmCommand
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("GetSsmSessionListCommand")
interface GetSsmSessionListCommand: SsmCommand {
    override val baseUrl: String
    override val channelId: String?
    override val chaincodeId: String?
    override val bearerToken: String?
    val dbName: String
    val ssm: String?
}

@JsExport
@JsName("GetSsmSessionListCommandBase")
class GetSsmSessionListCommandBase(
    override val baseUrl: String,
    override val dbName: String,
    override val channelId: String?,
    override val chaincodeId: String?,
    override val bearerToken: String?,
    override val ssm: String?
): GetSsmSessionListCommand
