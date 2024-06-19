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
import ssm.sdk.dsl.InvokeReturn
import ssm.sdk.dsl.SsmCmd

class SsmTxService(
	private val ssmService: SsmService,
) {
	private val logger = LoggerFactory.getLogger(SsmTxService::class.java)


	suspend fun sendRegisterUser(
		commands: List<UserRegisterCommand>
	): List<InvokeReturn> {
		return ssmService.signsAndSend {
			commands.map { command ->
				registerUser(command.agent, command.chaincodeUri, command.signerName)
			}
		}
	}

	suspend fun sendCreate(commands: List<SsmCreateCommand>): List<InvokeReturn> {
		return ssmService.signssAndSend {
			commands.map { command ->
				create(command.ssm, command.chaincodeUri, command.signerName)
			}
		}
	}

	suspend fun sendStart(commands: List<SsmStartCommand>): List<InvokeReturn> {
		return ssmService.signssAndSend {
			commands.map { command ->
				start(command.session, command.chaincodeUri, command.signerName)
			}

		}
	}

	suspend fun sendPerform(commands: List<SsmPerformCommand>): List<InvokeReturn> {
		return ssmService.signsAndSend {
			commands.map { command ->
				perform(command.action, command.context, command.chaincodeUri, command.signerName)
			}
		}
	}


	suspend fun sendRegisterUser(chaincodeUri: ChaincodeUri, agent: Agent, signerName: AgentName): InvokeReturn {
		return ssmService.signAndSend() {
			registerUser(agent, chaincodeUri, signerName)
		}
	}

	suspend fun sendCreate(chaincodeUri: ChaincodeUri, ssm: Ssm, signerName: AgentName): InvokeReturn {
		return ssmService.signAndSend() {
			create(ssm, chaincodeUri, signerName)
		}
	}

	suspend fun sendStart(chaincodeUri: ChaincodeUri, session: SsmSession, signerName: AgentName): InvokeReturn {
		return ssmService.signAndSend() {
			start(session, chaincodeUri, signerName)
		}
	}

	suspend fun sendPerform(
		chaincodeUri: ChaincodeUri, action: String, context: SsmContext, signerName: AgentName
	): InvokeReturn {
		return ssmService.signAndSend() {
			perform(action, context, chaincodeUri, signerName)
		}
	}


	fun registerUser(agent: Agent, chaincodeUri: ChaincodeUri, signerName: AgentName): SsmCmd {
		logger.info("Register user[${agent.name}] with signer[$signerName]")
		val cmd = RegisterCmd(agent)
		return cmd.commandToSign(chaincodeUri, signerName)
	}

	fun create(ssm: Ssm, chaincodeUri: ChaincodeUri, signerName: AgentName): SsmCmd {
		logger.info("Create ssm[${ssm.name}] with signer[$signerName]")
		val cmd = CreateCmd(ssm)
		return cmd.commandToSign(chaincodeUri, signerName)
	}

	fun start(session: SsmSession, chaincodeUri: ChaincodeUri, signerName: AgentName): SsmCmd {
		logger.info(
			"Start session[${session.session}] ssm[${session.ssm}] with signer[$signerName]"
		)
		val cmd = StartCmd(session)
		return cmd.commandToSign(chaincodeUri, signerName)
	}

	fun perform(action: String, context: SsmContext, chaincodeUri: ChaincodeUri, agentName: AgentName): SsmCmd {
		logger.info(
			"Perform action[${action}] session[${context.session}] with signer[$agentName]"
		)
		val cmd = PerformCmd(action, context)
		return cmd.commandToSign(chaincodeUri, agentName)
	}

}
