package ssm.data.dsl.config

import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.couchdb.dsl.config.SsmCouchdbConfig

/**
 * @d2 model
 * @title SSM Configuration
 * @parent [ssm.data.dsl.DataSsmD2]
 */
data class DataSsmConfig(
	/**
	 * Configuration for couchdb.
	 */
	val couchdb: SsmCouchdbConfig,
	/**
	 *  Configuration for couchdb.
	 */
	val chaincode: SsmChaincodeConfig,

	)
