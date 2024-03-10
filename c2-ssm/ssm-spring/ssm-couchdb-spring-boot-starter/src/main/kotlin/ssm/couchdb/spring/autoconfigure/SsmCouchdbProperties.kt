package ssm.couchdb.spring.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import ssm.couchdb.dsl.config.SsmCouchdbConfig

@ConfigurationProperties(prefix = "ssm")
data class SsmCouchdbProperties(
	val couchdb: SsmCouchdbConfig
)
