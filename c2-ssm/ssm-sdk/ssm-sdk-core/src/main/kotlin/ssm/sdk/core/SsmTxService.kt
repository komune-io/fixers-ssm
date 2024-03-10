package ssm.sdk.core

import org.slf4j.LoggerFactory
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmContext
import ssm.chaincode.dsl.model.SsmSession
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.sdk.core.invoke.command.CreateCmd
import ssm.sdk.core.invoke.command.PerformCmd
import ssm.sdk.core.invoke.command.RegisterCmd
import ssm.sdk.core.invoke.command.StartCmd
import ssm.sdk.core.ktor.SsmRequester
import ssm.sdk.dsl.InvokeReturn
import ssm.sdk.dsl.SsmCmd
import ssm.sdk.dsl.SsmCmdSigned
import ssm.sdk.sign.SsmCmdSigner

class SsmTxService(
	private val ssmRequester: SsmRequester,
	private val ssmCmdSigner: SsmCmdSigner
) {
	private val logger = LoggerFactory.getLogger(SsmTxService::class.java)

	fun registerUser(agent: Agent): SsmCmd {
		val cmd = RegisterCmd(agent)
		return cmd.commandToSign()
	}

	fun create(ssm: Ssm): SsmCmd {
		val cmd = CreateCmd(ssm)
		return cmd.commandToSign()
	}

	fun start( session: SsmSession): SsmCmd {
		val cmd = StartCmd(session)
		return cmd.commandToSign()
	}

	fun perform(action: String, context: SsmContext): SsmCmd {
		val cmd = PerformCmd(action, context)
		return cmd.commandToSign()
	}


	suspend fun sendRegisterUser(chaincodeUri: ChaincodeUri, agent: Agent, signerName: AgentName): InvokeReturn? {
		logger.info("Register user[${agent.name}] with signer[$signerName]")
		return signAndSend(chaincodeUri, signerName) {
			registerUser(agent)
		}
	}

	suspend fun sendCreate(chaincodeUri: ChaincodeUri, ssm: Ssm, signerName: AgentName): InvokeReturn? {
		logger.info("Create ssm[${ssm.name}] with signer[$signerName]")
		return signAndSend(chaincodeUri, signerName) {
			create(ssm)
		}
	}

	suspend fun sendStart(chaincodeUri: ChaincodeUri, session: SsmSession, signerName: AgentName): InvokeReturn? {
		logger.info("Start session[${session.session}] ssm[${session.ssm}] with signer[$signerName]")
		return signAndSend(chaincodeUri, signerName) {
			start(session)
		}
	}

	suspend fun sendPerform(
		chaincodeUri: ChaincodeUri, action: String, context: SsmContext, signerName: AgentName
	): InvokeReturn {
		logger.info("Perform action[${action}] session[${context.session}] with signer[$signerName]")
		return signAndSend(chaincodeUri, signerName) {
			perform(action, context)
		}
	}

	suspend fun signAndSend(chaincodeUri: ChaincodeUri, signerName: AgentName, build: () -> SsmCmd): InvokeReturn {
		return build().let { ssmCmd ->
			sign(ssmCmd, signerName)
		}.let { signed ->
			send(chaincodeUri, signed)
		}
	}

	fun sign(command: SsmCmd, signerName: AgentName): SsmCmdSigned {
		return ssmCmdSigner.sign(command, signerName)
	}

	suspend fun send(chaincodeUri: ChaincodeUri, ssmCommandSigned: SsmCmdSigned): InvokeReturn {
		return ssmRequester.invoke(chaincodeUri, ssmCommandSigned)
	}
}
