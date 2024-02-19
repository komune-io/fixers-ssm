plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	implementation("io.komune.ssm:ssm-data-spring-boot-starter:0.17.0-SNAPSHOT")
	implementation("io.komune.ssm:ssm-tx-spring-boot-starter:0.17.0-SNAPSHOT")

}
