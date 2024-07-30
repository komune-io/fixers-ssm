package ssm.api.features.query

import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import ssm.api.DataSsmQueryFunctionImpl
import ssm.bdd.config.SsmBddConfig
import ssm.chaincode.dsl.config.groupBy
import ssm.chaincode.dsl.model.uri.toSsmUri
import ssm.data.dsl.features.query.DataSsmGetQuery
import ssm.data.dsl.features.query.DataSsmListQuery
import ssm.data.dsl.features.query.DataSsmListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQuery
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionListQuery
import ssm.data.dsl.features.query.DataSsmSessionListQueryFunction

internal class DataSsmSessionGetQueryFunctionImplTest {

	private val dataSsmQueryFunction = DataSsmQueryFunctionImpl(
		SsmBddConfig.Data.config,
	)

	private val dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction
		= dataSsmQueryFunction.dataSsmSessionGetQueryFunction()


	@Test
	fun `test exception`() = runTest  {
		val queries = flowOf(
			DataSsmSessionGetQuery(
				"sessionName",
				SsmBddConfig.Chaincode.chaincodeUri.toSsmUri("ssmName")
			)
		)

		val ssmListResult = dataSsmSessionGetQueryFunction.invoke(queries).toList()

		Assertions.assertThat(ssmListResult).isEmpty()
	}

	@Test
	fun `test groupBy`() = runBlocking<Unit> {
		data class Item(val key: String, val value: Int)
		val flow = flowOf(
			Item("A", 1),
			Item("B", 2),
			Item("A", 3),
			Item("C", 4),
			Item("B", 5)
		)

		// Using the groupBy function
		val groupedFlow = flow.groupBy { it.key }

		// Collect and print the grouped items
		groupedFlow.collect { (key, itemsFlow) ->
			println("Group: $key")
			itemsFlow.collect { item ->
				println("  Item: $item")
			}
		}
	}
}
