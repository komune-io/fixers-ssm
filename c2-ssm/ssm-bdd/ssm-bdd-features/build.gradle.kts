plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":c2-ssm:ssm-sdk:ssm-sdk-core"))
	api(project(":c2-ssm:ssm-bdd:ssm-bdd-config"))

	io.komune.gradle.dependencies.FixersDependencies.Jvm.Test.junit(::api)
}
