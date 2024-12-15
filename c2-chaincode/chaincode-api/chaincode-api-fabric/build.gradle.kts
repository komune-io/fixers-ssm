plugins {
    id("io.komune.fixers.gradle.kotlin.jvm")
}

dependencies {

    Dependencies.jackson(::implementation)
    Dependencies.slf4j(::implementation)
    Dependencies.fabricSdk(::api)

    Dependencies.test(::testImplementation)
}
