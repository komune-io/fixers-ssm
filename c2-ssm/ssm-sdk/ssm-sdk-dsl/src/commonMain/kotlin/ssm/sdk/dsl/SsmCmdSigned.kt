package ssm.sdk.dsl

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
