package io.komune.c2.chaincode.api.fabric

import com.google.rpc.Status
import io.grpc.Metadata
import io.grpc.StatusRuntimeException
import io.komune.c2.chaincode.api.dsl.ChaincodeId
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.Endorser
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgs
import io.komune.c2.chaincode.api.dsl.invoke.InvokeException
import java.lang.System.currentTimeMillis
import java.util.StringJoiner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.hyperledger.fabric.client.EndorseException
import org.hyperledger.fabric.protos.gateway.ErrorDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class FabricGatewayClient(
    private val fabricGatewayBuilder: FabricGatewayBuilder,
) {

    private val logger: Logger = LoggerFactory.getLogger(FabricGatewayClient::class.java)

    @Throws(Exception::class)
    suspend fun query(
        endorsers: List<Endorser>,
        orgName: String,
        channelId: ChannelId,
        chaincodeId: ChaincodeId,
        invokeArgsList: List<InvokeArgs>
    ): List<String> = coroutineScope {
        val start = currentTimeMillis()
        val contract = fabricGatewayBuilder.contract(orgName, channelId, endorsers, chaincodeId)

        val proposalResponses = invokeArgsList.map { invokeArgs ->
            async(Dispatchers.IO) {
                val result = contract.evaluateTransaction(invokeArgs.function, *invokeArgs.values.toTypedArray())
                String(result)
            }
        }

        proposalResponses.awaitAll().also {
            logger.info("Transaction[${it.size}] sent in in ${currentTimeMillis() - start} ms")
        }

    }

    @Throws(Exception::class)
    suspend fun invoke(
        endorsers: List<Endorser>,
        orgName: String,
        channelId: ChannelId,
        chaincodeId: ChaincodeId,
        invokeArgsList: List<InvokeArgs>
    ): List<Transaction> = coroutineScope {
        logger.info("Invoke[${invokeArgsList.size}] transactions in [${channelId}:$chaincodeId]")
        val start = currentTimeMillis()
        val contract = fabricGatewayBuilder.contract(orgName, channelId, endorsers, chaincodeId)
        val proposal = invokeArgsList.map { invokeArgs ->
                try {
                     contract.newProposal(invokeArgs.function)
                        .addArguments(*invokeArgs.values.toTypedArray())
                        .build()
                        .endorse()
                } catch (e: EndorseException) {
                    val message = extractErrorMessage(e)
                    throw InvokeException(message, e)
                }

        }

        val asyncSubmit = proposal.map { tr ->
            async(Dispatchers.IO) {
                val start = currentTimeMillis()
                logger.info("Submit transaction[${tr.transactionId}] in [${channelId}:$chaincodeId]...")
                tr.submit()
                logger.info("Submitted transaction[${tr.transactionId}] " +
                        "in [${channelId}:$chaincodeId] in ${currentTimeMillis() - start} ms")
                Transaction(
                    tr.transactionId,
                    tr.result.toString()
                )
            }
        }
        asyncSubmit.awaitAll().also {
            logger.info("Transactions[${invokeArgsList.size}] completed in ${currentTimeMillis() - start} ms")
        }
    }

    private fun extractErrorMessage(e: EndorseException): String {
        val cause = e.cause as StatusRuntimeException
        val grpcStatusDetailsKey =
            Metadata.Key.of("grpc-status-details-bin", Metadata.BINARY_BYTE_MARSHALLER)
        val errors = StringJoiner(";")
        cause.trailers?.get(grpcStatusDetailsKey)?.let {
            val status: Status = Status.parseFrom(it)

            for (detail in status.detailsList) {
                if (detail.typeUrl == "type.googleapis.com/gateway.ErrorDetail") {
                    val details = ErrorDetail.parseFrom(detail.value)
                    errors.add(details.message.replace("chaincode response 500, ", ""))
                }
            }
        }
        return errors.toString()
    }

}

class Transaction(
    val transactionId: String,
    val body: String
)
