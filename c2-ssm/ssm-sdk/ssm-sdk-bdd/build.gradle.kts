import io.komune.gradle.dependencies.FixersDependencies

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":c2-ssm:ssm-sdk:ssm-sdk-core"))
	api(project(":c2-ssm:ssm-bdd:ssm-bdd-features"))
	api(project(":c2-ssm:ssm-chaincode:ssm-chaincode-dsl"))

	api(project(":c2-ssm:ssm-sdk:ssm-sdk-core"))
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")

	FixersDependencies.Jvm.Test.cucumber(::api)
}
