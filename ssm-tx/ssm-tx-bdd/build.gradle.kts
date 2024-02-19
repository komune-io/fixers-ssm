import io.komune.gradle.dependencies.FixersDependencies

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	implementation(project(":ssm-tx:ssm-tx-f2"))

	implementation(project(":ssm-sdk:ssm-sdk-bdd"))
	implementation(project(":ssm-chaincode:ssm-chaincode-bdd"))

	FixersDependencies.Jvm.Test.cucumber(::api)
}
