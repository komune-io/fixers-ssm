plugins {
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false
	kotlin("kapt") version PluginVersions.kotlin apply false

	id("lt.petuska.npm.publish") version PluginVersions.npmPublish apply false
	id("com.moowork.node") version "1.2.0"

	id("city.smartb.fixers.gradle.config")
	id("city.smartb.fixers.gradle.sonar")
	id("city.smartb.fixers.gradle.d2")
}

allprojects {
	group = "city.smartb.ssm"
	version = System.getenv("VERSION") ?: "latest"
	repositories {
		mavenCentral()
		mavenLocal()
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
	}
}

subprojects {
	plugins.withType(lt.petuska.npm.publish.NpmPublishPlugin::class.java).whenPluginAdded {
		the<lt.petuska.npm.publish.dsl.NpmPublishExtension>().apply {
			organization = "smartb"
			repositories {
				repository("npmjs") {
					registry = uri("https://registry.npmjs.org")
					authToken = System.getenv("NPM_TOKEN")
				}
			}
			publications {
				publication("js") {
					packageJson {
						bundledDependencies("kotlin") { // Always includes "kotlin" dependency and filters out the rest by the spec
							-"ktor-ktor.*".toRegex() // Exclude "kotlin-test" dependency
						}
					}
				}
			}
		}
	}
}

tasks {

	create<com.moowork.gradle.node.yarn.YarnTask>("installYarn") {
		dependsOn("build")
		args = listOf("install")
	}

	create<com.moowork.gradle.node.yarn.YarnTask>("storybook") {
		dependsOn("yarn_install")
		args = listOf("storybook")
	}

}

fixers {
	bundle {
		id = "ssm-tx"
		name = "Ssm Tx"
		description = "Wrapper around SSM"
		url = "https://gitlab.smartb.city/fixers/ssm"
	}
}