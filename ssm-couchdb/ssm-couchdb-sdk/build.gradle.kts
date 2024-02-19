plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-couchdb:ssm-couchdb-dsl"))
	api(project(":ssm-chaincode:ssm-chaincode-dsl"))
	api(project(":ssm-sdk:ssm-sdk-json"))

	api("com.ibm.cloud:cloudant:${Versions.cloudant}")

}
