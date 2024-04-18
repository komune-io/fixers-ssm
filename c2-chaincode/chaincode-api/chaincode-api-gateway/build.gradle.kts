plugins {
	id("io.spring.dependency-management")
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":c2-chaincode:chaincode-api:chaincode-api-fabric"))
//	implementation(project(":c2-chaincode:chaincode-api:chaincode-api-auth"))

	implementation("org.hyperledger.fabric-sdk-java:fabric-sdk-java:${Versions.fabric}")

    implementation ("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	Dependencies.jackson(::implementation)

	Dependencies.nettyTcnative(::runtimeOnly)

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
	testImplementation("io.projectreactor:reactor-test:${Versions.reactor}")
}
