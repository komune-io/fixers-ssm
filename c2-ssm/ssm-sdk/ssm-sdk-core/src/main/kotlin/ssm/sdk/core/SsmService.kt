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

class SsmService(
	private val ssmRequester: SsmRequester,
	private val ssmCmdSigner: SsmCmdSigner
) {

	suspend fun signAndSend(chaincodeUri: ChaincodeUri, signerName: AgentName, build: () -> SsmCmd): InvokeReturn {
		return build().let { ssmCmd ->
			sign(ssmCmd, signerName)
		}.let { signed ->
			send(chaincodeUri, signed)
		}
	}

	suspend fun signsAndSend(
		chaincodeUri: ChaincodeUri,
		signerName: AgentName,
		build: () -> List<SsmCmd>
	): List<InvokeReturn> {
		return build().map { ssmCmd ->
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

	suspend fun send(chaincodeUri: ChaincodeUri, ssmCommandSigneds: List<SsmCmdSigned>): List<InvokeReturn> {
		return ssmRequester.invoke(chaincodeUri, ssmCommandSigneds)
	}
}
