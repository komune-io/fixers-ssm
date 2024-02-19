plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-chaincode:ssm-chaincode-dsl"))
	api(project(":ssm-sdk:ssm-sdk-dsl"))
	api(project(":ssm-sdk:ssm-sdk-json"))
	api(project(":ssm-sdk:ssm-sdk-sign"))
	api(project(":ssm-sdk:ssm-sdk-sign-rsa-key"))

//	FixersDependencies.Jvm.Kotlin.ktorClient(::implementation)
//	api("io.ktor:ktor-client-content-negotiation:2.0.0")

	Dependencies.ktor(::implementation)

	testImplementation(project(":ssm-sdk:ssm-sdk-bdd"))
}
