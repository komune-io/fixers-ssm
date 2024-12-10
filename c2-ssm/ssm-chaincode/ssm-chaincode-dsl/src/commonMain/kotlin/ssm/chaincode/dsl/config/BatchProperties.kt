package ssm.chaincode.dsl.config

import f2.dsl.fnc.operators.Batch
import f2.dsl.fnc.operators.BATCH_DEFAULT_SIZE
import f2.dsl.fnc.operators.BATCH_DEFAULT_CONCURRENCY
import kotlin.js.JsExport
import kotlinx.serialization.Serializable

@JsExport
interface BatchPropertiesDTO {
    val size: Int
    val concurrency: Int
}

@JsExport
@Serializable
class BatchProperties(
    override val size: Int = BATCH_DEFAULT_SIZE,
    override val concurrency: Int = BATCH_DEFAULT_CONCURRENCY
):BatchPropertiesDTO

fun BatchProperties.toBatch(): Batch = Batch(size, concurrency)
