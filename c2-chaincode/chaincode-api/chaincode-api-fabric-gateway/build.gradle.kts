plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(":c2-chaincode:chaincode-api:chaincode-api-fabric"))
    Dependencies.jackson(::implementation)
    Dependencies.slf4j(::implementation)
    Dependencies.fabricSdkGateway(::api)

    implementation(platform("com.google.protobuf:protobuf-bom:4.29.2"))
    implementation( platform("io.grpc:grpc-bom:1.69.0"))
    compileOnly( "io.grpc:grpc-api")
    runtimeOnly( "io.grpc:grpc-netty")
    runtimeOnly( "io.grpc:grpc-netty-shaded")

    Dependencies.test(::testImplementation)
}
