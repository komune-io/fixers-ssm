package ssm.chaincode.f2.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.Protocol
import f2.client.ktor.get
import kotlin.js.Promise

@JsExport
@JsName("ssmClient")
fun ssmClient(protocol: Protocol, host: String, port: Int, path: String? = null): Promise<SSMFunctionClient> =
	F2ClientBuilder.get(protocol, host, port, path).then { s2Client ->
		SSMFunctionClient(s2Client)
	}

actual open class SSMFunctionClient actual constructor(val client: F2Client) : SSMRemoteFunction
