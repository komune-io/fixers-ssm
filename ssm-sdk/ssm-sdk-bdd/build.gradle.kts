import io.komune.gradle.dependencies.FixersDependencies

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":ssm-sdk:ssm-sdk-core"))
	api(project(":ssm-bdd:ssm-bdd-features"))
	api(project(":ssm-chaincode:ssm-chaincode-dsl"))

	api(project(":ssm-sdk:ssm-sdk-core"))
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")

	FixersDependencies.Jvm.Test.cucumber(::api)
}
