package io.komune.c2.chaincode.gateway.auth.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authorization.AuthorizationContext
import reactor.core.publisher.Mono

@Configuration
class WebSecurityConfig {

    companion object {
        const val SPRING_SECURITY_FILTER_CHAIN = "springSecurityFilterChain"
    }

    @Bean
    @ConfigurationProperties(prefix = "i2.filter")
    fun authFilter(): Map<String, String> = HashMap()

    @Bean(SPRING_SECURITY_FILTER_CHAIN)
    @ConditionalOnExpression(NO_AUTHENTICATION_REQUIRED_EXPRESSION)
    fun dummyAuthenticationProvider(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange {
            it.anyExchange().permitAll()
        }
        http.csrf {
            it.disable()
        }
        return http.build()
    }

    @Bean(SPRING_SECURITY_FILTER_CHAIN)
    @ConditionalOnExpression(AUTHENTICATION_REQUIRED_EXPRESSION)
    fun oauthAuthenticationProvider(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange{
            it.anyExchange()
                .access(::authenticate)
        }
        http.oauth2ResourceServer {
            it.jwt{}
        }
        http.csrf {
            it.disable()
        }
        return http.build()
    }

    private fun authenticate(
        authentication: Mono<Authentication>,
        context: AuthorizationContext
    ): Mono<AuthorizationDecision> {
        return authentication.map { auth ->
            if (auth !is JwtAuthenticationToken || auth.token == null) {
                return@map false
            }

            val filters = authFilter()
            filters.isEmpty() || filters.all { (key, value) -> auth.token.claims[key] == value }
        }.map(::AuthorizationDecision)
    }
}
