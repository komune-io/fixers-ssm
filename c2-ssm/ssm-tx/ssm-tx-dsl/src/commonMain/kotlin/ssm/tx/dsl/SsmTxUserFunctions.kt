package ssm.tx.dsl

import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction

/**
 * - fun ssmTxSessionPerformActionFunction(config: SsmChaincodeConfig): [SsmTxSessionPerformActionFunction]
 * @d2 model
 * @title User Agent command
 * @parent [ssm.tx.dsl.SsmTxD2]
 */
interface SsmTxUserFunctions {
	fun ssmTxSessionPerformActionFunction(config: SsmChaincodeConfig): SsmTxSessionPerformActionFunction
}
