package ssm.sdk.dsl

import ssm.chaincode.dsl.model.ChaincodeId
import ssm.chaincode.dsl.model.ChannelId
import ssm.chaincode.dsl.model.uri.ChaincodeUri

typealias SignerName = String

data class SsmCmdSigned(
	val cmd: SsmCmd,
	val signature: String,
	val signer: SignerName,
)

fun SsmCmdSigned.buildArgs(): InvokeArgs {
	return InvokeArgs(cmd.command.value,
		listOfNotNull(cmd.performAction, cmd.json, signer, signature))
}

fun SsmCmdSigned.buildCommandArgs(
	type: InvokeType,
	chaincodeUri: ChaincodeUri
	): InvokeCommandArgs {
	val args = buildArgs()
	return InvokeCommandArgs(
		cmd = type,
		chaincodeUri = chaincodeUri,
		fcn = args.fcn,
		args = args.args
	)
}
