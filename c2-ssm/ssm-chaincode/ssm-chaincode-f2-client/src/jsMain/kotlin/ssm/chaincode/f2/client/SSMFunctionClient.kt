package ssm.chaincode.f2.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.Protocol
import f2.client.ktor.get
import kotlin.js.Promise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@JsExport
actual open class SSMFunctionClient actual constructor(client: F2Client) : SSMRemoteFunction

@JsExport
@JsName("ssmClient")
fun ssmClient(
	protocol: Protocol, host: String, port: Int, path: String? = null
): Promise<SSMFunctionClient> = GlobalScope.promise {
	F2ClientBuilder.get(protocol, host, port, path).let { s2Client ->
		SSMFunctionClient(s2Client)
	}
}
