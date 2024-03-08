package ssm.chaincode.f2

import ssm.chaincode.f2.features.command.SsmTxCreateFunctionImpl
import ssm.chaincode.f2.features.command.SsmTxInitFunctionImpl
import ssm.chaincode.f2.features.command.SsmTxSessionStartFunctionImpl
import ssm.chaincode.f2.features.command.SsmUserGrantFunctionImpl
import ssm.chaincode.f2.features.command.SsmUserRegisterFunctionImpl
import ssm.sdk.core.SsmQueryService
import ssm.sdk.core.SsmTxService
import ssm.tx.dsl.SsmTxAdminFunctions
import ssm.tx.dsl.features.ssm.SsmTxCreateFunction
import ssm.tx.dsl.features.ssm.SsmTxInitFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction
import ssm.tx.dsl.features.user.SsmTxUserGrantFunction
import ssm.tx.dsl.features.user.SsmTxUserRegisterFunction

class SsmTxAdminServiceImpl(
	private val ssmTxService: SsmTxService,
	private val ssmQueryService: SsmQueryService,
	private val ssmUserGrantFunction: SsmUserGrantFunctionImpl = SsmUserGrantFunctionImpl(ssmTxService),
	private val ssmUserRegisterFunction: SsmUserRegisterFunctionImpl = SsmUserRegisterFunctionImpl(ssmTxService),
	private val ssmTxCreateFunction: SsmTxCreateFunctionImpl = SsmTxCreateFunctionImpl(ssmTxService),
	private val ssmTxSessionStartFunction: SsmTxSessionStartFunctionImpl = SsmTxSessionStartFunctionImpl(ssmTxService),
) : SsmTxAdminFunctions {

	override fun ssmTxUserGrantFunction(): SsmTxUserGrantFunction {
		return ssmUserGrantFunction
	}

		override fun ssmTxUserRegisterFunction(): SsmTxUserRegisterFunction {
		return ssmUserRegisterFunction
	}


	override fun ssmTxCreateFunction(): SsmTxCreateFunction {
		return ssmTxCreateFunction
	}

	override fun ssmTxInitializeFunction(): SsmTxInitFunction {
		return SsmTxInitFunctionImpl(ssmTxService, ssmQueryService)
	}

	override fun ssmTxSessionStartFunction(): SsmTxSessionStartFunction {
		return ssmTxSessionStartFunction
	}
}
