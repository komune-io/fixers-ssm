package io.komune.ssm.api.rest

import io.komune.ssm.api.fabric.exception.InvokeException
import io.komune.ssm.api.rest.config.ChainCodeId
import io.komune.ssm.api.rest.config.ChannelId
import io.komune.ssm.api.rest.chaincode.model.Cmd
import io.komune.ssm.api.rest.chaincode.model.ErrorResponse
import io.komune.ssm.api.rest.chaincode.model.InvokeParams
import io.komune.ssm.api.rest.chaincode.ChaincodeService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
class ChaincodeRestEndpoint(
	private val chaincodeService: ChaincodeService
) {
	companion object {
		const val CHANNEL_ID_URL_PARAM = "channelid"
		const val CHAINCODE_ID_URL_PARAM = "chaincodeid"
	}

	@GetMapping
	fun query(
		@RequestParam(name = CHANNEL_ID_URL_PARAM, required = false) channel: ChannelId?,
		@RequestParam(name = CHAINCODE_ID_URL_PARAM, required = false) chaincode: ChainCodeId?,
		cmd: Cmd,
		fcn: String,
		args: Array<String>
	): CompletableFuture<String> = chaincodeService.execute(InvokeParams(channel, chaincode, cmd, fcn, args))

	@PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
	fun invoke(
		@ModelAttribute args: InvokeParams
	): CompletableFuture<String> = chaincodeService.execute(args)

	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
	fun invokeJson(
		@RequestBody args: InvokeParams
	): CompletableFuture<String> = chaincodeService.execute(args)

	@ExceptionHandler(InvokeException::class)
	fun handleException(invokeException: InvokeException): ResponseEntity<ErrorResponse> {
		val error = ErrorResponse("Chaincode invoke error: ${invokeException.message}")
		return ResponseEntity(error, HttpStatus.BAD_REQUEST)
	}
}
