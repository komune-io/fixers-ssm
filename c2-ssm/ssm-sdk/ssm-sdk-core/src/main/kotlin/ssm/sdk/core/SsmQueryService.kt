package ssm.sdk.core

import com.fasterxml.jackson.core.type.TypeReference
import io.komune.c2.chaincode.dsl.Block
import io.komune.c2.chaincode.dsl.BlockId
import io.komune.c2.chaincode.dsl.ChaincodeUri
import io.komune.c2.chaincode.dsl.Transaction
import io.komune.c2.chaincode.dsl.TransactionId
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.AgentName
import ssm.chaincode.dsl.model.SessionName
import ssm.chaincode.dsl.model.Ssm
import ssm.chaincode.dsl.model.SsmName
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.model.SsmSessionStateLog
import ssm.sdk.core.invoke.query.AdminQuery
import ssm.sdk.core.invoke.query.AgentQuery
import ssm.sdk.core.invoke.query.BlockQuery
import ssm.sdk.core.invoke.query.LogQuery
import ssm.sdk.core.invoke.query.SessionQuery
import ssm.sdk.core.invoke.query.SsmQuery
import ssm.sdk.core.invoke.query.TransactionQuery
import ssm.sdk.core.ktor.SsmApiQuery
import ssm.sdk.core.ktor.SsmRequester
import ssm.sdk.json.JsonUtils

class SsmQueryService(private val ssmRequester: SsmRequester): SsmQueryServiceI {
	override suspend fun listAdmins(chaincodeUri: ChaincodeUri): List<AgentName> {
		val query = AdminQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	override suspend fun getAdmin(chaincodeUri: ChaincodeUri, username: AgentName): Agent? {
		val query = AdminQuery()
		return ssmRequester.query(chaincodeUri, username, query, Agent::class.java)
	}

	override suspend fun listUsers(chaincodeUri: ChaincodeUri): List<AgentName> {
		val query = AgentQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	override suspend fun getAgent(chaincodeUri: ChaincodeUri, agentName: AgentName): Agent? {
		val query = AgentQuery()
		return ssmRequester.query(chaincodeUri, agentName, query, Agent::class.java)
	}

	override suspend fun listSsm(chaincodeUri: ChaincodeUri): List<SsmName> {
		val query = SsmQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	override suspend fun getSsm(chaincodeUri: ChaincodeUri, name: SsmName): Ssm? {
		val query = SsmQuery()
		return ssmRequester.query(chaincodeUri, name, query, Ssm::class.java)
	}

	override suspend fun getSession(chaincodeUri: ChaincodeUri, sessionName: SessionName): SsmSessionState? {
		val query = SessionQuery()
		return ssmRequester.query(chaincodeUri, sessionName, query, SsmSessionState::class.java)
	}

	override suspend fun log(chaincodeUri: ChaincodeUri, sessionName: SessionName): List<SsmSessionStateLog> {
		val query = LogQuery()
		return ssmRequester.logger(chaincodeUri, sessionName, query, object : TypeReference<List<SsmSessionStateLog>>() {})
	}


	override suspend fun listSession(chaincodeUri: ChaincodeUri): List<String> {
		val query = SessionQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	override suspend fun listTransactions(chaincodeUri: ChaincodeUri): List<String> {
		val query = TransactionQuery()
		return ssmRequester.list(chaincodeUri, query, String::class.java)
	}

	override suspend fun getTransaction(chaincodeUri: ChaincodeUri, txId: TransactionId): Transaction? {
		val query = TransactionQuery()
		return ssmRequester.query(chaincodeUri, txId, query, Transaction::class.java)
	}

	override suspend fun listBlocks(chaincodeUri: ChaincodeUri): List<Int>  {
		val query = BlockQuery()
		return ssmRequester.list(chaincodeUri, query, Int::class.java)
	}

	override suspend fun getBlock(chaincodeUri: ChaincodeUri, blockId: BlockId): Block? {
		val query = BlockQuery()
		return ssmRequester.query(chaincodeUri, blockId.toString(), query, Block::class.java)
	}

	override suspend fun getAdmins(queries: List<GetAdminQuery>): List<Agent> {
		val query = AdminQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.username, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<Agent>>() {})
		}
	}

	override suspend fun getAgents(queries: List<GetAgentQuery>): List<Agent> {
		val query = AgentQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.agentName, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<Agent>>() {})
		}

	}

	override suspend fun getSsms(queries: List<GetSsmQuery>): List<Ssm> {
		val query = SsmQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.name, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<Ssm>>() {})
		}
	}
	override suspend fun getSessions(queries: List<GetSessionQuery>): List<SsmSessionState?> {
		val query = SessionQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.sessionName, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<String?>>() {})
		}.map { item ->
			item?.ifBlank { null }?.let { JsonUtils.mapper.readValue(it, SsmSessionState::class.java) }
		}

	}

	override suspend fun getTransactions(queries: List<GetTransactionQuery>): List<Transaction?> {
		val query = TransactionQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.txId, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<String?>>() {})
		}.map { item ->
			item?.let { JsonUtils.mapper.readValue(it, Transaction::class.java) }
		}

	}

	override suspend fun getBlocks(queries: List<GetBlockQuery>): List<Block> {
		val query = BlockQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.blockId.toString(), query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<Block>>() {})
		}

	}

	override suspend fun getLogs(queries: List<GetLogQuery>): List<List<SsmSessionStateLog>> {
		val query = LogQuery()
		return queries.map {
			SsmApiQuery(it.chaincodeUri, it.sessionName, query)
		}.let {
			ssmRequester.query(it, object : TypeReference<List<String>>() {})
		}.map { item ->
			item.let { JsonUtils.mapper.readValue(it, object : TypeReference<List<SsmSessionStateLog>>() {}) }
		}

	}
}
