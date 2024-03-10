plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":c2-ssm:ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-create-ssm-spring-boot-starter"))
	api(project(":c2-ssm:ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-init-ssm-spring-boot-starter"))
	api(project(":c2-ssm:ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-session-perform-action-spring-boot-starter"))
	api(project(":c2-ssm:ssm-spring:ssm-tx-spring-boot-starter:ssm-tx-session-start-spring-boot-starter"))

	testImplementation(project(":c2-ssm:ssm-bdd:ssm-bdd-spring-autoconfigure"))
}
