package ssm.sdk.dsl

import io.komune.c2.chaincode.dsl.ChaincodeUri

typealias SignerName = String

data class SsmCmdSigned(
    val cmd: SsmCmd,
    val signature: String,
    val signer: SignerName,
    val chaincodeUri: ChaincodeUri,
)

fun SsmCmdSigned.buildArgs(): InvokeArgs {
	return InvokeArgs(cmd.command.value,
		listOfNotNull(cmd.performAction, cmd.json, signer, signature))
}

fun SsmCmdSigned.buildCommandArgs(
	type: InvokeType,
	): InvokeCommandArgs {
	val args = buildArgs()
	return InvokeCommandArgs(
		cmd = type,
		chaincodeUri = chaincodeUri,
		fcn = args.fcn,
		args = args.args
	)
}
