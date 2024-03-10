package ssm.sdk.core

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties
import ssm.sdk.sign.FileUtils

class SsmSdkConfig(
	val baseUrl: String,
) {

	companion object {
		private const val SSM_REST_URL = "ssm.rest.url"

		@Throws(IOException::class)
		fun fromConfigFile(filename: String): SsmSdkConfig {
			val file = FileUtils.getUrl(filename)
			val props = Properties()
			props.load(FileInputStream(file.file))
			val baseUrl = props.getProperty(SSM_REST_URL)
			return SsmSdkConfig(baseUrl)
		}
	}
}
