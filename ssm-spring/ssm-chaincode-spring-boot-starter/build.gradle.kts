import io.komune.gradle.dependencies.FixersVersions

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.spring")
	kotlin("kapt")
}

dependencies {
	api(project(":ssm-chaincode:ssm-chaincode-f2"))

	api("io.komune.f2:f2-spring-boot-starter-function:${Versions.f2}")

	kapt("org.springframework.boot:spring-boot-configuration-processor:${FixersVersions.Spring.boot}")

	testImplementation(project(":ssm-bdd:ssm-bdd-spring-autoconfigure"))
}
