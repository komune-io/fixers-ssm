package io.komune.c2.chaincode.api.gateway.chaincode.model

import io.komune.c2.chaincode.api.config.utils.JsonUtils


data class InvokeReturn(val status: String, val info: String, val transactionId: String) {
	fun toJson(): String {
		return JsonUtils.toJson(this);
	}
}
