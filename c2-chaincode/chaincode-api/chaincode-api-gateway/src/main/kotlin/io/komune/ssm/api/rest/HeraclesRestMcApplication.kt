package io.komune.ssm.api.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [HeraclesRestMcApplication::class] )
class HeraclesRestMcApplication

fun main(args: Array<String>) {
	runApplication<HeraclesRestMcApplication>(*args)
}