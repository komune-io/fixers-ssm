package ssm.sdk.core.invoke.builder

import ssm.sdk.core.invoke.query.SsmQueryName
import ssm.sdk.dsl.InvokeArgs

interface HasQueryName {
	val queryName: SsmQueryName
}

interface HasGet: HasQueryName {
	fun queryArgs(username: String): InvokeArgs {
		return InvokeArgs(queryName.value, listOf(username))
	}

}

interface HasList: HasQueryName {

	companion object {
		const val LIST_FUNCTION = "list"
	}

	fun listArgs(): InvokeArgs {
		return InvokeArgs(LIST_FUNCTION, listOf(queryName.value))
	}
}

open class QueryBuilder(override val queryName: SsmQueryName): HasQueryName
