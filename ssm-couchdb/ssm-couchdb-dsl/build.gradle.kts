plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
//	id("dev.petuska.npm.publish")
}

dependencies {
	commonMainApi(project(":ssm-chaincode:ssm-chaincode-dsl"))
}
