package ssm.tx.bdd

import f2.dsl.fnc.invoke
import io.cucumber.java8.En
import ssm.bdd.config.SsmCommandStep
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmSession
import ssm.chaincode.f2.SsmTxAdminServiceImpl
import ssm.sdk.sign.model.SignerUser
import ssm.tx.dsl.features.ssm.SsmCreateCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartCommand
import ssm.tx.dsl.features.user.SsmUserRegisterCommand


class TxSteps : SsmCommandStep(), En {

	companion object {
		const val GLUE = "ssm.tx.bdd"
	}
	init {
		prepareSteps()

	}

	override suspend fun startSession(session: SsmSession) {
		getSsmTxAdminServiceImpl().ssmTxSessionStartFunction().invoke(
			SsmSessionStartCommand(
				chaincodeUri = bag.chaincodeUri,
				signerName = bag.adminSigner.name,
				session
			)
		)
	}

	override suspend fun createSsm(ssm: Ssm) {
		getSsmTxAdminServiceImpl().ssmTxCreateFunction().invoke(
			SsmCreateCommand(
				chaincodeUri = bag.chaincodeUri,
				signerName = bag.adminSigner.name,
				ssm = ssm
			)
		)
	}

	override suspend fun registerUser(agent: Agent) {
		getSsmTxAdminServiceImpl().ssmTxUserRegisterFunction().invoke(
			SsmUserRegisterCommand(
				chaincodeUri = bag.chaincodeUri,
				signerName = bag.adminSigner.name,
				agent
			)
		)
	}

	override suspend fun loadSigner(userName: AgentName, filename: String): SignerUser {
		return SignerUser.loadFromFile(userName, filename)
	}

	fun getSsmTxAdminServiceImpl() = SsmTxAdminServiceImpl(bag.clientTx(bag.adminSigner), bag.clientQuery)
}
