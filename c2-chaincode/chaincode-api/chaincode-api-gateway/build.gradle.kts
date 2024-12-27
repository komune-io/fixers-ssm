plugins {
	id("io.spring.dependency-management")
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":c2-chaincode:chaincode-api:chaincode-api-fabric"))
	implementation(project(":c2-chaincode:chaincode-api:chaincode-api-fabric-gateway"))

	Dependencies.springWebFlux(::implementation)
	Dependencies.f2Auth(::implementation)
	Dependencies.f2Http(::implementation)
	Dependencies.jackson(::implementation)

	Dependencies.springTest(::testImplementation)
}
