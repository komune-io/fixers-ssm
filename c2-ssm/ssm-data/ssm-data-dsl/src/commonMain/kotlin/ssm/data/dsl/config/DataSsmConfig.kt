package ssm.data.dsl.config

import f2.dsl.fnc.operators.CHUNK_DEFAULT_SIZE
import ssm.chaincode.dsl.config.BatchProperties
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.couchdb.dsl.config.SsmCouchdbConfig

/**
 * @d2 model
 * @title SSM Configuration
 * @parent [ssm.data.dsl.DataSsmD2]
 */
data class DataSsmConfig(
	/**
	 * Configuration for chunking.
	 */
	val batch: BatchProperties,
	/**
	 * Configuration for couchdb.
	 */
	val couchdb: SsmCouchdbConfig,
	/**
	 *  Configuration for couchdb.
	 */
	val chaincode: SsmChaincodeConfig,

	)
