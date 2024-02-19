plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	implementation(project(":ssm-couchdb:ssm-couchdb-f2"))
	api(project(":ssm-bdd:ssm-bdd-features"))

	testImplementation(project(":ssm-tx:ssm-tx-bdd"))
}
