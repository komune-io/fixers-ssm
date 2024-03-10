package ssm.api.features.query

import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import ssm.api.DataSsmQueryFunctionImpl
import ssm.bdd.config.SsmBddConfig
import ssm.data.dsl.features.query.DataSsmListQuery
import ssm.data.dsl.features.query.DataSsmListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionListQuery
import ssm.data.dsl.features.query.DataSsmSessionListQueryFunction

internal class DataSsmSessionListQueryFunctionImplTest {

	private val dataSsmQueryFunction = DataSsmQueryFunctionImpl(
		SsmBddConfig.Data.config,
	)

	private val function: DataSsmSessionListQueryFunction = dataSsmQueryFunction.dataSsmSessionListQueryFunction()
	private val dataSsmListQueryFunction: DataSsmListQueryFunction = dataSsmQueryFunction.dataSsmListQueryFunction()


	@Test
	fun `test exception`() = runBlocking<Unit> {
		val ssmListResult = dataSsmListQueryFunction.invoke(
			DataSsmListQuery(listOf(SsmBddConfig.Chaincode.chaincodeUri))
		)
		val result = DataSsmSessionListQuery(
			ssmUri = ssmListResult.items.first().uri,
		).invokeWith(function)
		Assertions.assertThat(result.items).isNotEmpty()
	}
}
