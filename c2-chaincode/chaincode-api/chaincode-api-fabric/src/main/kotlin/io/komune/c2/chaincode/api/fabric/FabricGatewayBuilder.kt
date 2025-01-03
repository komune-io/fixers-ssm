package io.komune.c2.chaincode.api.fabric

import io.grpc.CallOptions
import io.grpc.Grpc
import io.grpc.TlsChannelCredentials
import io.komune.c2.chaincode.api.config.FabricConfigLoader
import io.komune.c2.chaincode.api.config.properties.OrganisationProperties
import io.komune.c2.chaincode.api.config.properties.PeerProperties
import io.komune.c2.chaincode.api.dsl.ChaincodeId
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.Endorser
import io.komune.c2.chaincode.api.fabric.extention.asFileReader
import java.io.IOException
import java.security.InvalidKeyException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import org.hyperledger.fabric.client.Contract
import org.hyperledger.fabric.client.Gateway
import org.hyperledger.fabric.client.Hash
import org.hyperledger.fabric.client.identity.Identities
import org.hyperledger.fabric.client.identity.Identity
import org.hyperledger.fabric.client.identity.Signer
import org.hyperledger.fabric.client.identity.Signers
import org.hyperledger.fabric.client.identity.X509Identity
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FabricGatewayBuilder(
    private val cryptoConfigBase: String,
    private val fabricConfigLoader: FabricConfigLoader,
) {

    private val logger: Logger = LoggerFactory.getLogger(FabricGatewayClient::class.java)

    fun contract(
        orgName: String,
        channelId: ChannelId,
        endorsers: List<Endorser>,
        chaincodeId: ChaincodeId
    ): Contract {
        try {
            val gateway = gateway(orgName, channelId, endorsers)
            val network = gateway.getNetwork(channelId)
            val contract = network.getContract(chaincodeId)
            return contract
        } catch (e: Throwable) {
            logger.error("Error while quering of channel [$channelId]", e)
            throw e
        }
    }

    fun gateway(organizationName: String, channelId: ChannelId, endorsers: List<Endorser>): Gateway {
        val fabricConfig = fabricConfigLoader.getFabricConfig(channelId)
        val organizationConfig = fabricConfig.network.organisations[organizationName]!!
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
            .blockAndPrivateDataEventsOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.MINUTES
                )
            }.blockEventsOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.MINUTES
                )
            }
            .chaincodeEventsOptions {  options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.SECONDS
                ) }
            .commitStatusOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.MINUTES
                )
            }
            .endorseOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.SECONDS
                )
            }
            .evaluateOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.SECONDS
                )
            }
            .filteredBlockEventsOptions {  options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.SECONDS
                ) }
            .submitOptions { options: CallOptions ->
                @Suppress("MagicNumber")
                options.withDeadlineAfter(
                    240,
                    TimeUnit.SECONDS
                )
            }
            .connect()
    }

    @Throws(IOException::class, CertificateException::class)
    private fun newIdentity(orgConfig: OrganisationProperties, config: PeerProperties): Identity {
        val url = config.getSigncertsAsUrl(cryptoConfigBase)
        return url.asFileReader().use { reader ->
            val certificate = Identities.readX509Certificate(reader)
            X509Identity(orgConfig.mspid, certificate)
        }
    }

    @Throws(IOException::class, InvalidKeyException::class)
    private fun newSigner(config: PeerProperties): Signer {
        val url = config.getKeystoreAsUrl(cryptoConfigBase)
        return url.asFileReader().use { reader ->
            val privateKey = Identities.readPrivateKey(reader)
            Signers.newPrivateKeySigner(privateKey)
        }
    }

}
