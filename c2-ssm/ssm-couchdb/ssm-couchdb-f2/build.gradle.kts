plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":c2-ssm:ssm-couchdb:ssm-couchdb-dsl"))
	api(project(":c2-ssm:ssm-couchdb:ssm-couchdb-sdk"))

	Dependencies.slf4j(::implementation)

	testImplementation(project(":c2-ssm:ssm-couchdb:ssm-couchdb-bdd"))
}
