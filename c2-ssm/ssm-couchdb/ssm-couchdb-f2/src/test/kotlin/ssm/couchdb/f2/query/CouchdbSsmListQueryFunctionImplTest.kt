package ssm.couchdb.f2.query

import f2.dsl.fnc.invokeWith
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import ssm.couchdb.bdd.TestConfig
import ssm.couchdb.dsl.query.CouchdbSsmListQuery
import ssm.couchdb.dsl.query.CouchdbSsmListQueryFunction

internal class CouchdbSsmListQueryFunctionImplTest : FunctionTestBase() {

	var couchdbSsmListQueryFunctionImpl: CouchdbSsmListQueryFunction = queries.couchdbSsmListQueryFunction()

	@Test
	fun `must return all ssm`(): Unit = runBlocking {
		val ssmList = CouchdbSsmListQuery(
			pagination = null,
			channelId = TestConfig.CHANNEL_ID,
			chaincodeId = TestConfig.CHAINCODE_ID
		).invokeWith(couchdbSsmListQueryFunctionImpl)

		Assertions.assertThat(ssmList).isNotNull
		Assertions.assertThat(ssmList.items).isNotNull
	}
}
