plugins {
	id("io.komune.fixers.gradle.kotlin.jvm")
	id("io.komune.fixers.gradle.publish")
}

dependencies {

	api("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonKotlin}")

}
