plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	api(project(":ssm-tx:ssm-tx-dsl"))

	api(project(":ssm-sdk:ssm-sdk-sign"))
	api(project(":ssm-sdk:ssm-sdk-core"))

}
