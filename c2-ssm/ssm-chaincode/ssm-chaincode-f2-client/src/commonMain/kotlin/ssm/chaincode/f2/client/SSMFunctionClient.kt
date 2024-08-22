package ssm.chaincode.f2.client

import f2.client.F2Client
import f2.client.ktor.F2ClientBuilder
import f2.dsl.fnc.F2SupplierSingle

fun ssmClient(urlBase: String,): F2SupplierSingle<SSMFunctionClient> = F2SupplierSingle {
    F2ClientBuilder.get(urlBase).let { s2Client ->
        SSMFunctionClient(s2Client)
    }
}
interface SSMRemoteFunction {
	// fun perform(): SsmPerformRemoteFunction
}

open class SSMFunctionClient(val client: F2Client) : SSMRemoteFunction {

    // override fun perform() = object : SsmPerformRemoteFunction {
    //  	override suspend fun invoke(cmd: SsmPerformCommand): SsmPerformResult = client.executeInvoke("perform" ,cmd)
    // }
}
