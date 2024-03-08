package ssm.sdk.core.ktor

import com.fasterxml.jackson.core.type.TypeReference
import org.slf4j.LoggerFactory
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.sdk.core.invoke.builder.HasGet
import ssm.sdk.core.invoke.builder.HasList
import ssm.sdk.dsl.InvokeReturn
import ssm.sdk.dsl.SsmCmdSigned
import ssm.sdk.dsl.buildArgs
import ssm.sdk.json.JSONConverter
import ssm.sdk.json.JsonUtils

class SsmRequester(
	private val jsonConverter: JSONConverter,
	private val coopRepository: KtorRepository,
) {

	private val logger = LoggerFactory.getLogger(SsmRequester::class.java)

	suspend fun <T> log(chaincodeUri: ChaincodeUri, value: String, query: HasGet, clazz: TypeReference<List<T>>): List<T> {
		val args = query.queryArgs(value)
		logger.info(
			"Query[$QUERY] the blockchain in chaincode[${chaincodeUri.uri}] with fcn[${args.fcn}] with args:${args.args}",
		)
		val request = coopRepository.query(
			cmd = QUERY,
			fcn = args.fcn,
			args = args.args,
			channelId = chaincodeUri.channelId,
			chaincodeId = chaincodeUri.chaincodeId,
		)
		return request.let {
			JsonUtils.toObject(it, clazz)
		}
	}

	suspend fun <T> query(chaincodeUri: ChaincodeUri, value: String, query: HasGet, clazz: Class<T>): T? {
		val args = query.queryArgs(value)
		val request = coopRepository.query(
			cmd = QUERY,
			fcn = args.fcn,
			args = args.args,
			channelId = chaincodeUri.channelId,
			chaincodeId = chaincodeUri.chaincodeId,
		)
		logger.info(
			"Query the blockchain in chaincode[${chaincodeUri.uri}] with fcn[${args.fcn}] with args:${args.args}",
		)
		return request.let { jsonConverter.toCompletableObject(clazz, it) }
	}

	suspend fun <T> list(chaincodeUri: ChaincodeUri, query: HasList, clazz: Class<T>): List<T> {
		val args = query.listArgs()
		val request = coopRepository.query(
			cmd = QUERY,
			fcn = args.fcn,
			args = args.args,
			channelId = chaincodeUri.channelId,
			chaincodeId = chaincodeUri.chaincodeId,
		)
		logger.info(
			"Query the blockchain in chaincode[${chaincodeUri.uri}] with fcn[${args.fcn}] with args:${args.args}",
		)
		return request.let { response ->
			jsonConverter.toCompletableObjects(clazz, response)
		}
	}

	@Throws(Exception::class)
	suspend operator fun invoke(chaincodeUri: ChaincodeUri, cmdSigned: SsmCmdSigned): InvokeReturn {
		val invokeArgs = cmdSigned.buildArgs()
		logger.info(
			"""
            Invoke the blockchain in channel[${chaincodeUri.chaincodeId}]  with command[${invokeArgs.fcn}] 
            with args:$invokeArgs
        """.trimIndent()
		)
		return coopRepository.invoke(
			cmd = INVOKE,
			fcn = invokeArgs.fcn,
			args = invokeArgs.args,
			channelId = chaincodeUri.channelId,
			chaincodeId = chaincodeUri.chaincodeId,
		).let { jsonConverter.toCompletableObject(InvokeReturn::class.java, it)!! }
	}

	companion object {
		const val QUERY = "query"
		const val INVOKE = "invoke"
	}
}
