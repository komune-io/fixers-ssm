package io.komune.ssm.api.rest.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(HeraclesConfigProps::class)
class HeraclesAutoConfiguration {

	@Bean
	fun builder(coopConfig: HeraclesConfigProps): FabricClientBuilder {
		return FabricClientBuilder(coopConfig)
	}

}
