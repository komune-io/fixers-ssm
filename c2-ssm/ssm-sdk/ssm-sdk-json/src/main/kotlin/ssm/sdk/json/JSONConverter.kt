package ssm.sdk.json

import com.fasterxml.jackson.core.type.TypeReference

interface JSONConverter {
	fun <T> toCompletableObjects(clazz: Class<T>, value: String): List<T>
	fun <T> toCompletableObject(clazz: Class<T>, value: String): T?
	fun <T> toObject(clazz: Class<T>, value: String): T?
}
