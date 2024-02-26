plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":ssm-sdk:ssm-sdk-core"))
	api(project(":ssm-bdd:ssm-bdd-config"))

	io.komune.gradle.dependencies.FixersDependencies.Jvm.Test.junit(::api)
}
