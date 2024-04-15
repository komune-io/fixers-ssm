package io.komune.c2.chaincode.api.gateway.chaincode.model

import io.komune.c2.chaincode.api.gateway.config.ChainCodeId
import io.komune.c2.chaincode.api.gateway.config.ChannelId

data class InvokeParams(
    val channelid: io.komune.c2.chaincode.api.gateway.config.ChannelId? = null,
    val chaincodeid: io.komune.c2.chaincode.api.gateway.config.ChainCodeId? = null,
    val cmd: Cmd,
    val fcn: String,
    val args: Array<String>
)
