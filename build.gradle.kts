import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.palantir.git-version") version "0.15.0"
}

group = "dev.m2en"

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val details = versionDetails()
version = details.lastTag.substring(1)
val implementationVersion = "$version(${details.gitHash})"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:5.0.0-alpha.22")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
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
            attributes("Implementation-Version" to implementationVersion)
        }

        archiveFileName.set("citation.jar")
    }
}
