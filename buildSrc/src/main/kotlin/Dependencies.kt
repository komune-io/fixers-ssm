import io.komune.gradle.dependencies.FixersPluginVersions
import io.komune.gradle.dependencies.FixersVersions
import io.komune.gradle.dependencies.FixersDependencies
import io.komune.gradle.dependencies.Scope
import io.komune.gradle.dependencies.add
import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.net.URI

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	val d2 = FixersPluginVersions.fixers
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	const val graalvm = FixersPluginVersions.graalvm
	const val npmPublish = FixersPluginVersions.npmPublish
}

object Versions {

	val f2 = PluginVersions.fixers

	const val slf4j = FixersVersions.Logging.slf4j
	const val jackson = FixersVersions.Json.jackson
	const val jacksonKotlin = FixersVersions.Json.jacksonKotlin

	const val springBoot = FixersVersions.Spring.boot
	const val springSecurity = FixersVersions.Spring.security
	const val reactor = FixersVersions.Spring.reactor

	const val ktor = FixersVersions.Kotlin.ktor

	const val fabric = "2.2.26"

	const val cloudant = "0.3.1"
	const val bouncycastleVersion = "1.70"
//	const val nettyTcnative = "2.0.48.Final"

	const val junit = FixersVersions.Test.junit
	const val assertj = FixersVersions.Test.assertj
}

fun RepositoryHandler.defaultRepo() {
	mavenCentral()
	maven { url = URI("https://s01.oss.sonatype.org/content/repositories/snapshots") }
	maven { url = URI("https://repo.spring.io/milestone") }
}

object Dependencies {
	fun slf4j(scope: Scope) = FixersDependencies.Jvm.Logging.slf4j(scope)
	// TODO Migrate to f2-client
	fun ktor(scope: Scope) = scope.add(
		"io.ktor:ktor-client-core:${Versions.ktor}",
		"io.ktor:ktor-client-content-negotiation:${Versions.ktor}",
		"io.ktor:ktor-client-logging:${Versions.ktor}",
		"io.ktor:ktor-client-cio:${Versions.ktor}",
		"io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}",
		"io.ktor:ktor-serialization-jackson:${Versions.ktor}"
	)
	fun jackson(scope: Scope) = scope.add(
		"com.fasterxml.jackson.module:jackson-module-kotlin:${FixersVersions.Json.jacksonKotlin}"
	)
	fun test(scope: Scope) = scope.add(
		"org.junit.jupiter:junit-jupiter:${Versions.junit}",
		"org.junit.jupiter:junit-jupiter-api:${Versions.junit}",
		"org.assertj:assertj-core:${Versions.assertj}"
	)

//	fun nettyTcnative(scope: Scope) = scope.add(
//		"io.netty:netty-tcnative-boringssl-static:${Versions.nettyTcnative}",
//		"io.netty:netty-tcnative-classes:${Versions.nettyTcnative}"
//	)
}
