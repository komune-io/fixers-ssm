plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation ("org.slf4j:slf4j-api:${Versions.slf4J}")
    implementation ("org.hyperledger.fabric-sdk-java:fabric-sdk-java:${Versions.fabric}")
    implementation( "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")

    Dependencies.nettyTcnative(::runtimeOnly)

    Dependencies.test(::testImplementation)
}
