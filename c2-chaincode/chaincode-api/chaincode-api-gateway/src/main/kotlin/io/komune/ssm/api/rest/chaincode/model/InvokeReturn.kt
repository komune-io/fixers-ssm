package io.komune.ssm.api.rest.chaincode.model

import io.komune.ssm.api.fabric.utils.JsonUtils


data class InvokeReturn(val status: String, val info: String, val transactionId: String) {
	fun toJson(): String {
		return JsonUtils.toJson(this);
	}
}
