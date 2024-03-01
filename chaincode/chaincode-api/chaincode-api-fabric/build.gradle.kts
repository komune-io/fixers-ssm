plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    implementation ("org.slf4j:slf4j-api:${Versions.slf4J}")
    implementation ("org.hyperledger.fabric-sdk-java:fabric-sdk-java:${Versions.fabric}")
    implementation( "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    runtimeOnly("io.netty:netty-tcnative-boringssl-static:2.0.48.Final")


    Dependencies.test(::testImplementation)
}