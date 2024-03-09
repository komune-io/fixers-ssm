package io.komune.ssm.api.rest.chaincode.model

import io.komune.ssm.api.rest.config.ChainCodeId
import io.komune.ssm.api.rest.config.ChannelId

data class InvokeParams(
    val channelid: ChannelId? = null,
    val chaincodeid: ChainCodeId? = null,
    val cmd: Cmd,
    val fcn: String,
    val args: Array<String>
)