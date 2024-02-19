plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-chaincode:ssm-chaincode-dsl"))
	api(project(":ssm-sdk:ssm-sdk-core"))

	testImplementation(project(":ssm-chaincode:ssm-chaincode-bdd"))
	testImplementation(project(":ssm-tx:ssm-tx-bdd"))
}
