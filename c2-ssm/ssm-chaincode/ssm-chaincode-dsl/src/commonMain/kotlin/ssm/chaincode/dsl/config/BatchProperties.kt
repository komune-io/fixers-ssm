package ssm.chaincode.dsl.config

import f2.dsl.fnc.operators.Batch
import f2.dsl.fnc.operators.BATCH_DEFAULT_SIZE
import f2.dsl.fnc.operators.BATCH_DEFAULT_CONCURRENCY
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@JsExport
@Serializable
class BatchProperties {
    val size: Int = BATCH_DEFAULT_SIZE
    val concurrency: Int = BATCH_DEFAULT_CONCURRENCY
}

fun BatchProperties.toBatch(): Batch = Batch(size, concurrency)
