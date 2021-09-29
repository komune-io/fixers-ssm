package ssm.data.dsl

import ssm.data.dsl.config.DataSsmConfig
import ssm.data.dsl.features.query.DataSsmGetQueryFunction
import ssm.data.dsl.features.query.DataSsmListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionListQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionLogGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionLogListQueryFunction

/**
 * - fun dataSsmListQueryFunction(config: DataSsmConfig): [DataSsmListQueryFunction]
 * - fun dataSsmGetQueryFunction(config: DataSsmConfig): [DataSsmGetQueryFunction]
 * - fun dataSsmSessionListQueryFunction(config: DataSsmConfig): [DataSsmSessionListQueryFunction]
 * - fun dataSsmSessionGetQueryFunction(config: DataSsmConfig): [DataSsmSessionGetQueryFunction]
 * - fun dataSsmSessionLogListQueryFunction(config: DataSsmConfig): [DataSsmSessionLogListQueryFunction]
 * - fun dataSsmSessionLogGetQueryFunction(config: DataSsmConfig): [DataSsmSessionLogGetQueryFunction]
 * @d2 model
 * @title Entrypoints
 * @page
 * Synthesis and global objects of the API
 * @@title SSM-TX/General
 */
expect interface SsmApiQueryFunctions {
	fun dataSsmListQueryFunction(config: DataSsmConfig): DataSsmListQueryFunction
	fun dataSsmGetQueryFunction(config: DataSsmConfig): DataSsmGetQueryFunction
	fun dataSsmSessionListQueryFunction(config: DataSsmConfig): DataSsmSessionListQueryFunction
	fun dataSsmSessionGetQueryFunction(config: DataSsmConfig): DataSsmSessionGetQueryFunction
	fun dataSsmSessionLogListQueryFunction(config: DataSsmConfig): DataSsmSessionLogListQueryFunction
	fun dataSsmSessionLogGetQueryFunction(config: DataSsmConfig): DataSsmSessionLogGetQueryFunction
}
