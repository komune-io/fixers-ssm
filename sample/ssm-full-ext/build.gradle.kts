plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	implementation("io.komune.c2:ssm-data-spring-boot-starter:0.18.0-SNAPSHOT")
	implementation("io.komune.c2:ssm-tx-spring-boot-starter:0.18.0-SNAPSHOT")

}
