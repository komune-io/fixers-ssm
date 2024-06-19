package ssm.sdk.core

import java.io.IOException
import ssm.sdk.core.auth.BearerTokenAuthCredentials
import ssm.sdk.core.ktor.KtorRepository
import ssm.sdk.core.ktor.SsmRequester
import ssm.sdk.json.JSONConverter
import ssm.sdk.json.JSONConverterObjectMapper
import ssm.sdk.sign.SsmCmdSigner

class SsmServiceFactory(
	private var coopRepository: KtorRepository,
	private var jsonConverter: JSONConverterObjectMapper,
) {

	fun buildQueryService(): SsmQueryService {
		return SsmQueryService(SsmRequester(jsonConverter, coopRepository))
	}

	fun buildTxService(ssmCmdSigner: SsmCmdSigner): SsmTxService {
		val ssmService = SsmService(SsmRequester(jsonConverter, coopRepository),
			ssmCmdSigner)
		return SsmTxService(ssmService)
	}

	companion object {
		@Throws(IOException::class)
		fun builder(filename: String, bearerTokenHeaderProvider: BearerTokenAuthCredentials? = null): SsmServiceFactory {
			val config = SsmSdkConfig.fromConfigFile(filename)
			return builder(config, bearerTokenHeaderProvider)
		}

		fun builder(config: SsmSdkConfig, bearerTokenHeaderProvider: BearerTokenAuthCredentials? = null): SsmServiceFactory {
			val coopRepository = KtorRepository(config.baseUrl, bearerTokenHeaderProvider)
			val converter = JSONConverterObjectMapper()
			return SsmServiceFactory(
				coopRepository = coopRepository,
				jsonConverter = converter,
			)
		}
	}
}
