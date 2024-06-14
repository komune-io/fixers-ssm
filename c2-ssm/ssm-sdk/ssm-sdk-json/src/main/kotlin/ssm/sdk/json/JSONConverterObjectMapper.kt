package ssm.sdk.json

import com.fasterxml.jackson.core.type.TypeReference
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.CompletionException
import ssm.sdk.json.JsonUtils.mapper

class JSONConverterObjectMapper : JSONConverter {


	override fun <T> toCompletableObjects(clazz: Class<T>, value: String): List<T> {
		try {
			val type: TypeReference<List<T>> = object : TypeReference<List<T>>() {}
			 return JsonUtils.toObject(value, type)
		} catch (e: IOException) {
			throw CompletionException("Error parsing response: $value", e)
		}
	}

	override fun <T> toCompletableObject(clazz: Class<T>, value: String): T? {
		return toObject(clazz, value)
	}

	override fun <T> toObject(clazz: Class<T>, value: String): T?  {
		return try {
			if (value.isBlank()) {
				null
			} else {
				JsonUtils.toObject(value, clazz)
			}
		} catch (e: IOException) {
			throw CompletionException("Error parsing response: $value", e)
		}
	}
}
