plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
	id("lt.petuska.npm.publish")
}

dependencies {
	commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
	commonMainApi("city.smartb.f2:f2-dsl-function:${Versions.f2}")
}
