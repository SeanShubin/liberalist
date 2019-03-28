import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
}

group = "org.liberalist.website"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("com.vladsch.flexmark", "flexmark-all", "0.34.46")
    compile("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.9.8")
    compile(group = "com.amazonaws", name = "aws-java-sdk-s3", version = "1.11.528")
    testImplementation(kotlin("test-junit"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
