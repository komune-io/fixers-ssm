package io.komune.ssm.api.rest.blockchain.model

import io.komune.ssm.api.rest.blockchain.model.IdentitiesInfoModel
import org.hyperledger.fabric.sdk.BlockInfo
import java.util.Date

class TransactionModel(
    val transactionId: String,
    val blockId: Long,
    val timestamp: Date,
    val isValid: Boolean,
    val channelId: String,
    val creator: IdentitiesInfoModel,
    val nonce: ByteArray,
    val type: BlockInfo.EnvelopeType,
    val validationCode: Byte,
)
