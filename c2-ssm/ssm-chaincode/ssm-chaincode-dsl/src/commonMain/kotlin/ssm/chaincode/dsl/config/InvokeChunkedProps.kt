package ssm.chaincode.dsl.config

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow

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

fun <R> Flow<List<R>>.flattenConcurrently(): Flow<R> = flatMapMerge { list ->
    list.asFlow() // Convert each list to a flow and flatten it
}
