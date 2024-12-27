package io.komune.c2.chaincode.api.fabric

import io.grpc.CallOptions
import io.grpc.Grpc
import io.grpc.TlsChannelCredentials
import io.komune.c2.chaincode.api.fabric.config.FabricConfig
import io.komune.c2.chaincode.api.fabric.config.OrganisationConfig
import io.komune.c2.chaincode.api.fabric.config.PeerConfig
import io.komune.c2.chaincode.api.fabric.extention.asFileReader
import io.komune.c2.chaincode.api.fabric.model.Endorser
import io.komune.c2.chaincode.api.fabric.model.InvokeArgs
import java.io.IOException
import java.lang.System.currentTimeMillis
import java.security.InvalidKeyException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.hyperledger.fabric.client.Contract
import org.hyperledger.fabric.client.Gateway
import org.hyperledger.fabric.client.Hash
import org.hyperledger.fabric.client.SubmittedTransaction
import org.hyperledger.fabric.client.identity.Identities
import org.hyperledger.fabric.client.identity.Identity
import org.hyperledger.fabric.client.identity.Signer
import org.hyperledger.fabric.client.identity.Signers
import org.hyperledger.fabric.client.identity.X509Identity
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class FabricGatewayClientSuspend(
    private val cryptoConfigBase: String,
    private val fabricConfig: FabricConfig,
) {

    private val logger: Logger = LoggerFactory.getLogger(FabricChainCodeClient::class.java)


    @Throws(Exception::class)
    suspend fun invoke(
        endorsers: List<Endorser>,
        orgName: String,
        channelName: String,
        chainId: String,
        invokeArgsList: List<InvokeArgs>
    ): List<SubmittedTransaction> {
        val start = currentTimeMillis()
        val gateway = getGateway(orgName, endorsers)
        val network = gateway.getNetwork(channelName)
        val contract = network.getContract(chainId)
        return invokeBlockChain(contract, invokeArgsList).let {
            logger.info("Transactions[${invokeArgsList.size}] completed in ${currentTimeMillis() - start} ms")
            it
        }
    }

    private suspend fun invokeBlockChain(
        contract: Contract, invokeArgsList: List<InvokeArgs>
    ): List<SubmittedTransaction> = coroutineScope {

        val start = currentTimeMillis()
        val proposalResponses = invokeArgsList.mapIndexed { index, invokeArgs ->
            async(Dispatchers.IO) {
                contract.newProposal(  invokeArgs.function)
                    .addArguments(*invokeArgs.values.toTypedArray())
                    .build()
                    .endorse()
                    .submitAsync()
            }
        }

        proposalResponses.awaitAll().also {
            logger.info("Transaction[${it.size}] sent in in ${currentTimeMillis() - start} ms")
        }
    }


    @Throws(java.lang.Exception::class)
    fun getGateway(organizationName: String?, endorsers: List<Endorser>): Gateway {
        val organizationConfig: OrganisationConfig = fabricConfig.network.getOrganisation(organizationName)
        val trustManager = organizationConfig.ca.getTlsCacertsAsUrl(cryptoConfigBase)

        val credentials = TlsChannelCredentials.newBuilder()
            .trustManager(trustManager.openStream())
            .build()
        val endorser = endorsers.first()
        val peerConfig = organizationConfig.peers[endorser.peer]
        val requests = peerConfig!!.requests.removePrefix("grpcs://")
        val channel = Grpc.newChannelBuilder(requests, credentials)
            .build()

        return Gateway.newInstance()
            .identity(newIdentity(organizationConfig, peerConfig))
            .signer(newSigner(peerConfig))
            .hash(Hash.SHA256)
            .connection(channel)
            .evaluateOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    5,
                    TimeUnit.SECONDS
                )
            }
            .endorseOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    15,
                    TimeUnit.SECONDS
                )
            }
            .submitOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    5,
                    TimeUnit.SECONDS
                )
            }
            .commitStatusOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    1,
                    TimeUnit.MINUTES
                )
            }
            .connect()
    }

    @Throws(IOException::class, CertificateException::class)
    private fun newIdentity(orgConfig: OrganisationConfig, config: PeerConfig): Identity {
        val url = config.getSigncertsAsUrl(cryptoConfigBase)
        return url.asFileReader().use { reader ->
            val certificate = Identities.readX509Certificate(reader)
            X509Identity(orgConfig.mspid, certificate)
        }
    }

    @Throws(IOException::class, InvalidKeyException::class)
    private fun newSigner(config: PeerConfig): Signer {
        val url = config.getKeystoreAsUrl(cryptoConfigBase)
        return url.asFileReader().use { reader ->
            val privateKey = Identities.readPrivateKey(reader)
            Signers.newPrivateKeySigner(privateKey)
        }
    }

}
