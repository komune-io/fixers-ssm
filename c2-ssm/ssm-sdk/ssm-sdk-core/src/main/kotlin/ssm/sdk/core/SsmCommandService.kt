package ssm.sdk.core

import org.slf4j.LoggerFactory
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmContext
import ssm.chaincode.dsl.model.SsmSession
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.sdk.core.command.SsmCreateCommand
import ssm.sdk.core.command.SsmPerformCommand
import ssm.sdk.core.command.SsmStartCommand
import ssm.sdk.core.command.UserRegisterCommand
import ssm.sdk.core.invoke.command.CreateCmd
import ssm.sdk.core.invoke.command.PerformCmd
import ssm.sdk.core.invoke.command.RegisterCmd
import ssm.sdk.core.invoke.command.StartCmd
import ssm.sdk.core.ktor.SsmRequester
import ssm.sdk.dsl.InvokeReturn
import ssm.sdk.dsl.SsmCmd
import ssm.sdk.dsl.SsmCmdSigned
import ssm.sdk.sign.SsmCmdSigner

class SsmCommandService(
	private val ssmService: SsmService,
) {
	private val logger = LoggerFactory.getLogger(SsmCommandService::class.java)

	suspend fun sendRegisterUser(
		chaincodeUri: ChaincodeUri,
		signerName: AgentName,
		commands: List<UserRegisterCommand>
	): List<InvokeReturn> {
		return ssmService.signsAndSend(chaincodeUri, signerName) {
			commands.map { command ->
				logger.info("Register user[${command.agent.name}] with signer[${signerName}]")
				registerUser(command.agent)
			}
		}
	}

	suspend fun sendCreate(
		chaincodeUri: ChaincodeUri,
		signerName: AgentName,
		commands: List<SsmCreateCommand>
	): List<InvokeReturn> {
		return ssmService.signsAndSend(chaincodeUri, signerName) {
			commands.map { command ->
				logger.info("Create ssm[${command.ssm.name}] with signer[$command.signerName]")
				create(command.ssm)
			}
		}
	}

	suspend fun sendStart(
		chaincodeUri: ChaincodeUri,
		signerName: AgentName,
		commands: List<SsmStartCommand>
	): List<InvokeReturn> {
		return ssmService.signsAndSend(chaincodeUri, signerName) {
			commands.map { command ->
				logger.info(
					"Start session[${command.session.session}] ssm[${command.session.ssm}] with signer[$command.signerName]"
				)
				start(command.session)
			}

		}
	}

	suspend fun sendPerform(
		chaincodeUri: ChaincodeUri,
		signerName: AgentName,
		commands: List<SsmPerformCommand>
	): List<InvokeReturn> {
		return ssmService.signsAndSend(chaincodeUri, signerName) {
			commands.map { command ->
				logger.info(
					"Perform action[${command.action}] session[${command.context.session}] with signer[$command.signerName]"
				)
				perform(command.action, command.context)
			}
		}
	}

	private fun registerUser(agent: Agent): SsmCmd {
		val cmd = RegisterCmd(agent)
		return cmd.commandToSign()
	}

	private fun create(ssm: Ssm): SsmCmd {
		val cmd = CreateCmd(ssm)
		return cmd.commandToSign()
	}

	private fun start(session: SsmSession): SsmCmd {
		val cmd = StartCmd(session)
		return cmd.commandToSign()
	}

	private fun perform(action: String, context: SsmContext): SsmCmd {
		val cmd = PerformCmd(action, context)
		return cmd.commandToSign()
	}

}
