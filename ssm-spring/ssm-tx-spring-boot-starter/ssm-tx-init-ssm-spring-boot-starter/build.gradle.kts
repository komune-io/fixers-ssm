import io.komune.gradle.dependencies.FixersVersions

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.spring")
	kotlin("kapt")
}

dependencies {
	api(project(":ssm-tx:ssm-tx-f2"))
	api(project(":ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-config-spring-boot-starter"))
	api("io.komune.f2:f2-spring-boot-starter-function:${Versions.f2}")
	kapt("org.springframework.boot:spring-boot-configuration-processor:${FixersVersions.Spring.boot}")

	testImplementation(project(":ssm-bdd:ssm-bdd-spring-autoconfigure"))
}
