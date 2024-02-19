plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-couchdb:ssm-couchdb-dsl"))
	api(project(":ssm-couchdb:ssm-couchdb-sdk"))

	Dependencies.slf4j(::implementation)

	testImplementation(project(":ssm-couchdb:ssm-couchdb-bdd"))
}
