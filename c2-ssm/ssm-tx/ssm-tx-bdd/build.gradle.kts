import io.komune.gradle.dependencies.FixersDependencies

plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	implementation(project(":c2-ssm:ssm-tx:ssm-tx-f2"))

	implementation(project(":c2-ssm:ssm-sdk:ssm-sdk-bdd"))
	implementation(project(":c2-ssm:ssm-chaincode:ssm-chaincode-bdd"))

	FixersDependencies.Jvm.Test.cucumber(::api)
}
