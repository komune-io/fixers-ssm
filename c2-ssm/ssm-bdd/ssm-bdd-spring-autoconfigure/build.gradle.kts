plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":c2-ssm:ssm-bdd:ssm-bdd-config"))
}
