plugins {
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":chaincode:chaincode-api:chaincode-api-fabric"))
	implementation(project(":chaincode:chaincode-api:chaincode-api-rest-i2-keycloak"))

	implementation("org.hyperledger.fabric-sdk-java:fabric-sdk-java:${Versions.fabric}")

    implementation ("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")
	implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
	Dependencies.jackson(::implementation)

	testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
	testImplementation("io.projectreactor:reactor-test:${Versions.reactor}")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
	imageName.set("ghcr.io/komune-io/chaincode-api-gateway:${this.project.version}")
}
