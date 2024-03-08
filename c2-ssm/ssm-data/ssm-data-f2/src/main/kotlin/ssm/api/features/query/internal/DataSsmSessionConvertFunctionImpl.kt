package ssm.api.features.query.internal

import f2.dsl.fnc.F2Function
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ssm.api.extentions.getSessionLogs
import ssm.api.extentions.getTransaction
import ssm.chaincode.dsl.blockchain.Transaction
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.model.uri.SsmUri
import ssm.chaincode.dsl.model.uri.asChaincodeUri
import ssm.chaincode.dsl.query.SsmGetSessionLogsQueryFunction
import ssm.chaincode.dsl.query.SsmGetTransactionQueryFunction
import ssm.data.dsl.model.DataChannel
import ssm.data.dsl.model.DataSsmSession
import ssm.data.dsl.model.DataSsmSessionState

class DataSsmSessionConvertFunctionImpl(
	private val ssmGetSessionLogsQueryFunction: SsmGetSessionLogsQueryFunction,
	private val ssmGetTransactionQueryFunction: SsmGetTransactionQueryFunction
) : F2Function<DataSsmSessionConvertQuery, DataSsmSession> {

	override suspend fun invoke(msg: Flow<DataSsmSessionConvertQuery>): Flow<DataSsmSession> =
		msg.map { payload ->
			val sessionLogs =
				payload.sessionState.session.getSessionLogs(payload.ssmUri, ssmGetSessionLogsQueryFunction)
			val transactions = sessionLogs.mapNotNull { it.txId.getTransaction(ssmGetTransactionQueryFunction,
				chaincodeUri = payload.ssmUri.asChaincodeUri()
			) }
			val firstTransaction = transactions.minByOrNull { transaction ->
				transaction.timestamp
			}
			val lastTransaction = transactions.maxByOrNull { transaction ->
				transaction.timestamp
			}
			payload.toDataSession(payload.ssmUri, firstTransaction, lastTransaction, transactions)
		}

	fun DataSsmSessionConvertQuery.toDataSession(
		ssmUri: SsmUri, firstTransaction: Transaction?, lastTransaction: Transaction?, transactions: List<Transaction>
	): DataSsmSession {
		return DataSsmSession(
			ssmUri = ssmUri,
			sessionName = this.sessionState.session,
			state = DataSsmSessionState(
				details = this.sessionState,
				transaction = lastTransaction
			),
			channel = DataChannel(ssmUri.channelId),
			transaction = firstTransaction,
			transactions = transactions
		)
	}
}

data class DataSsmSessionConvertQuery(
	val ssmUri: SsmUri,
	val sessionState: SsmSessionState
)
