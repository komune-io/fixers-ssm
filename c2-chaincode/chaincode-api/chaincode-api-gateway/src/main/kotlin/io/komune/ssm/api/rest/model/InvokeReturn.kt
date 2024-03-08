package io.komune.ssm.api.rest.model

import io.komune.ssm.api.fabric.utils.JsonUtils

data class InvokeReturn(val status: String, val info: String, val transactionId: String) {
	fun toJson(): String {
		return JsonUtils.toJson(this);
	}
}
