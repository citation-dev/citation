import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.m2en"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("dev.kord:kord-core:0.8.0-M15")
    implementation("org.slf4j:slf4j-simple:2.0.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
        }
    }

    shadowJar {
        manifest {
            attributes("Main-Class" to "dev.m2en.citation.MainKt")
        }

        archiveFileName.set("citation.jar")
    }
}

