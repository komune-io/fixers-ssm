package io.komune.c2.chaincode.api.gateway.chaincode.model

import io.komune.c2.chaincode.api.gateway.config.ChainCodeId
import io.komune.c2.chaincode.api.gateway.config.ChannelId

data class InvokeParams(
    val channelid: ChannelId? = null,
    val chaincodeid: ChainCodeId? = null,
    val cmd: Cmd,
    val fcn: String,
    val args: Array<String>
)
