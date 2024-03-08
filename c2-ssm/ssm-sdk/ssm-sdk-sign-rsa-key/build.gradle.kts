plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {

	api(project(":c2-ssm:ssm-sdk:ssm-sdk-sign"))
	implementation("org.bouncycastle:bcprov-jdk15on:${Versions.bouncycastleVersion}")

}
