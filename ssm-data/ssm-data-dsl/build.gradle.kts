plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	commonMainApi(project(":ssm-couchdb:ssm-couchdb-dsl"))
	commonMainApi(project(":ssm-chaincode:ssm-chaincode-dsl"))
}
