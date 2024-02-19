
plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api("org.springframework.boot:spring-boot-starter-webflux:${io.komune.gradle.dependencies.FixersVersions.Spring.boot}")
	implementation("io.komune.ssm:ssm-data-spring-boot-starter:0.2.1")
	implementation("io.komune.ssm:ssm-tx-spring-boot-starter:0.2.1")

}
