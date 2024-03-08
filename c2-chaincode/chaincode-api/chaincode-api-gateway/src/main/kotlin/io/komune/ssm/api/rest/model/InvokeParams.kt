package io.komune.ssm.api.rest.model

data class InvokeParams(
    val cmd: Cmd,
    val fcn: String,
    val args: Array<String>
)