plugins {
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false
	kotlin("kapt") version PluginVersions.kotlin apply false
	id("org.springframework.boot") version PluginVersions.springBoot apply false
	id("org.graalvm.buildtools.native") version PluginVersions.graalvm apply false

	id("dev.petuska.npm.publish") version PluginVersions.npmPublish apply false
	id("com.moowork.node") version "1.2.0"

	id("io.komune.fixers.gradle.config") version PluginVersions.fixers
	id("io.komune.fixers.gradle.sonar") version PluginVersions.fixers
	id("io.komune.fixers.gradle.d2") version PluginVersions.d2
}

allprojects {
	group = "io.komune.ssm"
	version = System.getenv("VERSION") ?: "experimental-SNAPSHOT"
}

subprojects {
	plugins.withType(dev.petuska.npm.publish.NpmPublishPlugin::class.java).whenPluginAdded {
		the<dev.petuska.npm.publish.extension.NpmPublishExtension>().apply {
			organization.set("smartb")
			registries {
				register("npmjs") {
					uri.set(uri("https://registry.npmjs.org"))
					authToken.set(java.lang.System.getenv("NPM_TOKEN"))
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
	d2 {
		outputDirectory = file("storybook/stories/d2/")
	}
	bundle {
		id = "ssm-data"
		name = "Ssm Data"
		description = "Aggregate all ssm data source to optimize request"
		url = "https://gitlab.smartb.city/fixers/ssm"
	}
}
