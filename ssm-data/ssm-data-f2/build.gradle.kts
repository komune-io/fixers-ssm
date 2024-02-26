plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-data:ssm-data-dsl"))

	api(project(":ssm-couchdb:ssm-couchdb-f2"))
	api(project(":ssm-chaincode:ssm-chaincode-f2"))

	testImplementation(project(":ssm-data:ssm-data-bdd"))
}
