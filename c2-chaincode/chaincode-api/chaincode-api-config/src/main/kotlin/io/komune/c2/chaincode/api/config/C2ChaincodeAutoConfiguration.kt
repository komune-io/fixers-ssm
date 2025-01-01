package io.komune.c2.chaincode.api.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(C2ChaincodeConfiguration::class)
@Configuration(proxyBeanMethods = false)
class C2ChaincodeAutoConfiguration {

    @Bean
    fun fabricConfigLoader(coopConfig: C2ChaincodeConfiguration): FabricConfigLoader {
        return FabricConfigLoader(coopConfig)
    }
}
