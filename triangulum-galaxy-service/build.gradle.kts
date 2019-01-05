import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val helidonVersion: String by project
val logbackVersion: String by project
val junitVersion: String by project

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    kotlin("jvm")
    id("io.spring.dependency-management")
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("io.helidon.webserver:helidon-webserver-bundle")
    implementation("io.helidon.config:helidon-config-yaml")
    api("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

dependencyManagement {
    imports {
        mavenBom("io.helidon:helidon-bom:$helidonVersion")
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
