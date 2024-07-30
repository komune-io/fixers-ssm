package ssm.chaincode.dsl.config

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class InvokeChunkedProps(
    val size: Int = 128
)

fun <T, R> Flow<T>.chunk(
    props: InvokeChunkedProps,
    fnc: suspend (t: List<T>) -> List<R>
): Flow<List<R>> = flow {
    val buffer = mutableListOf<T>()
    collect { value ->
        buffer.add(value)
        if (buffer.size == props.size) {
            emit(fnc(ArrayList(buffer))) // Apply fnc to the chunk and emit the result
            buffer.clear()
        }
    }
    if (buffer.isNotEmpty()) {
        emit(fnc(ArrayList(buffer))) // Apply fnc to remaining elements and emit the result
    }
}

fun <T> Flow<T>.chunk(props: InvokeChunkedProps): Flow<List<T>> = chunk(props, {it})

fun <R> Flow<List<R>>.flattenConcurrentlyList(
    concurrency: Int = DEFAULT_CONCURRENCY
): Flow<R> = flatMapMerge(concurrency) { list ->
    list.asFlow() // Convert each list to a flow and flatten it
}

fun <R> Flow<Flow<R>>.flattenConcurrentlyFlow(
    concurrency: Int = DEFAULT_CONCURRENCY
): Flow<R> = flattenMerge(concurrency)

fun <T, K> Flow<T>.groupBy(keySelector: (T) -> K): Flow<Pair<K, Flow<T>>> = channelFlow {
    val groups = mutableMapOf<K, Channel<T>>()

    // Launch a coroutine to collect the original flow
    launch {
        collect { value ->
            val key = keySelector(value)
            val groupChannel = groups.getOrPut(key) {
                Channel<T>(Channel.UNLIMITED).also { channel ->
                    // For each new group, send a new flow to the downstream collector
                    launch {
                        send(key to channel.consumeAsFlow())
                    }
                }
            }
            groupChannel.send(value)
        }
        // Close all channels after the original flow collection is complete
        groups.values.forEach { it.close() }
    }
}
