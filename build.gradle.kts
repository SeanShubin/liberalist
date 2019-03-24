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
//    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
//    testImplementation(kotlin("test-common"))
//    testImplementation(kotlin("test-annotations-common"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
