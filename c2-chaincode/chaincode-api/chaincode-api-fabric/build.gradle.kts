plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {
    implementation(project(":c2-chaincode:chaincode-api:chaincode-api-config"))
    implementation(project(":c2-chaincode:chaincode-api:chaincode-api-dsl"))

    Dependencies.jackson(::implementation)
    Dependencies.slf4j(::implementation)
//    Dependencies.fabricSdkGateway(::api)


    // Enforce platform versions
    implementation(enforcedPlatform("io.grpc:grpc-bom:1.67.1"))
    implementation(enforcedPlatform("com.google.protobuf:protobuf-bom:4.28.2"))
    // Main dependencies
    api("org.hyperledger.fabric:fabric-gateway:1.7.1") {
        exclude(group = "org.hyperledger.fabric", module = "fabric-protos")
    }
    implementation("org.hyperledger.fabric:fabric-protos:0.3.2")

    // Explicitly declare protobuf and grpc dependencies
    implementation("com.google.protobuf:protobuf-java")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-netty-shaded")

    // Add constraints to force versions
//    constraints {
//        implementation( implementation("org.hyperledger.fabric:fabric-protos")) {
//            version {
//                strictly("0.3.1")
//            }
//        }
//        implementation("io.grpc:grpc-protobuf") {
//            version {
//                strictly("1.67.1")
//            }
//        }
//        implementation("com.google.protobuf:protobuf-java") {
//            version {
//                strictly("4.28.2")
//            }
//        }
//    }
//
//    // Optional: exclude transitive grpc-protobuf if still having issues
//    configurations.all {
//        resolutionStrategy {
//            force("io.grpc:grpc-protobuf:1.67.1")
//            force("com.google.protobuf:protobuf-java:4.28.2")
//        }
//    }

    Dependencies.test(::testImplementation)
}
