package ssm.sdk.core

import com.fasterxml.jackson.core.type.TypeReference
import ssm.chaincode.dsl.blockchain.Block
import ssm.chaincode.dsl.blockchain.BlockId
import ssm.chaincode.dsl.blockchain.Transaction
import ssm.chaincode.dsl.blockchain.TransactionId
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.SessionName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmName
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.model.SsmSessionStateLog
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.sdk.core.invoke.query.AdminQuery
import ssm.sdk.core.invoke.query.AgentQuery
import ssm.sdk.core.invoke.query.BlockQuery
import ssm.sdk.core.invoke.query.LogQuery
import ssm.sdk.core.invoke.query.SessionQuery
import ssm.sdk.core.invoke.query.SsmQuery
import ssm.sdk.core.invoke.query.TransactionQuery
import ssm.sdk.core.ktor.SsmRequester

class SsmQueryService(private val ssmRequester: SsmRequester) {
	suspend fun listAdmins(chaincodeUri: ChaincodeUri): List<AgentName> {
		val query = AdminQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	suspend fun getAdmin(chaincodeUri: ChaincodeUri, username: AgentName): Agent? {
		val query = AdminQuery()
		return ssmRequester.query(chaincodeUri, username, query, Agent::class.java)
	}

	suspend fun listUsers(chaincodeUri: ChaincodeUri): List<AgentName> {
		val query = AgentQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	suspend fun getAgent(chaincodeUri: ChaincodeUri, agentName: AgentName): Agent? {
		val query = AgentQuery()
		return ssmRequester.query(chaincodeUri, agentName, query, Agent::class.java)
	}

	suspend fun listSsm(chaincodeUri: ChaincodeUri): List<SsmName> {
		val query = SsmQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	suspend fun getSsm(chaincodeUri: ChaincodeUri, name: SsmName): Ssm? {
		val query = SsmQuery()
		return ssmRequester.query(chaincodeUri, name, query, Ssm::class.java)
	}

	suspend fun getSession(chaincodeUri: ChaincodeUri,sessionName: SessionName): SsmSessionState? {
		val query = SessionQuery()
		return ssmRequester.query(chaincodeUri, sessionName, query, SsmSessionState::class.java)
	}

	suspend fun log(chaincodeUri: ChaincodeUri, sessionName: SessionName): List<SsmSessionStateLog> {
		val query = LogQuery()
		return ssmRequester.log(chaincodeUri, sessionName, query, object : TypeReference<List<SsmSessionStateLog>>() {})
	}

	suspend fun listSession(chaincodeUri: ChaincodeUri): List<String> {
		val query = SessionQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	suspend fun getTransaction(chaincodeUri: ChaincodeUri, txId: TransactionId): Transaction? {
		val query = TransactionQuery()
		return ssmRequester.query(chaincodeUri, txId, query, Transaction::class.java)
	}

	suspend fun getBlock(chaincodeUri: ChaincodeUri, blockId: BlockId): Block? {
		val query = BlockQuery()
		return ssmRequester.query(chaincodeUri, blockId.toString(), query, Block::class.java)
	}
}
