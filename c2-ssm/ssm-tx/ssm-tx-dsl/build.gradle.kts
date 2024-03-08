plugins {
	id("io.komune.fixers.gradle.kotlin.mpp")
	id("io.komune.fixers.gradle.publish")
}

dependencies {
	commonMainApi(project(":c2-ssm:ssm-chaincode:ssm-chaincode-dsl"))
}
