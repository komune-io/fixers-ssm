plugins {
    id("io.spring.dependency-management")
    id("io.komune.fixers.gradle.kotlin.jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-reflect")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api ("org.springframework.boot:spring-boot-starter-security:${Versions.springBoot}")
    implementation ("org.springframework.security:spring-security-oauth2-resource-server:${Versions.springSecurity}")
    implementation( "org.springframework.security:spring-security-oauth2-jose:${Versions.springSecurity}")

    implementation ("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")

    implementation ("io.projectreactor:reactor-core:${Versions.reactor}")
}
