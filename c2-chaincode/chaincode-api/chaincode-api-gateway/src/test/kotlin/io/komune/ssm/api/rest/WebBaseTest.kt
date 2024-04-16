package io.komune.ssm.api.rest

import io.komune.c2.chaincode.api.gateway.HeraclesRestMcApplication
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.util.UriComponentsBuilder


@ExtendWith(SpringExtension::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [HeraclesRestMcApplication::class]
)
class WebBaseTest {

    @LocalServerPort
    internal lateinit var port: Integer

    @Autowired
    internal lateinit var restTemplate: TestRestTemplate

    internal fun baseUrl(): UriComponentsBuilder {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:$port")
    }
}
