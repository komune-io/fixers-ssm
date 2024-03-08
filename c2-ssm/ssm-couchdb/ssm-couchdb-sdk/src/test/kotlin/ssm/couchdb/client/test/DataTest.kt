package ssm.couchdb.client.test

import ssm.couchdb.client.CouchdbSsmClient
import ssm.couchdb.client.builder.SsmCouchDbBasicAuth

object DataTest {
	val dbSsmName = "sandbox_ssm"
	val ssmName = "ssm"

	private val username = "couchdb"
	private val password = "couchdb"
	private val serviceUrl = "http://localhost:5984"

	var ssmCouchDbClient: CouchdbSsmClient = CouchdbSsmClient.builder()
		.withUrl(serviceUrl)
		.withName("Ssm Sdk Unit Test")
		.withAuth(
			SsmCouchDbBasicAuth(
			username = username,
			password = password,
		)
		).build()
}