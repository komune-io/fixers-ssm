package io.komune.c2.chaincode.api.gateway.chaincode.model

import io.komune.c2.chaincode.api.dsl.ChaincodeId
import io.komune.c2.chaincode.api.dsl.ChannelId
import io.komune.c2.chaincode.api.dsl.invoke.InvokeArgs


data class InvokeParams(
    val channelid: ChannelId? = null,
    val chaincodeid: ChaincodeId? = null,
    val cmd: Cmd,
    val fcn: String,
    val args: Array<String>
)


fun List<InvokeParams>.toInvokeArgs(): List<InvokeArgs> = map {
    it.toInvokeArgs()
}

fun InvokeParams.toInvokeArgs(): InvokeArgs {
    return InvokeArgs(fcn, args.toList())
}

