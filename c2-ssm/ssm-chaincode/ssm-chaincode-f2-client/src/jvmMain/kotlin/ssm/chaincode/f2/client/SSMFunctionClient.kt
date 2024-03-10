package ssm.chaincode.f2.client

import f2.client.F2Client

actual open class SSMFunctionClient actual constructor(val client: F2Client) : SSMRemoteFunction {

	// override fun perform() = object : SsmPerformRemoteFunction {
	//  	override suspend fun invoke(cmd: SsmPerformCommand): SsmPerformResult = client.executeInvoke("perform" ,cmd)
	// }
}
