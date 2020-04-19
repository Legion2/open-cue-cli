plugins {
    kotlin("jvm") version "1.3.72"
    application
}

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/ktor")
}

application {
    mainClassName = "io.github.legion2.open_cue_cli.MainKt"
    executableDir = ""
    applicationName = "open-cue-cli"
}

val ktorVersion = "1.3.2"
val cliktVersion = "2.6.0"

dependencies {
    implementation("com.github.ajalt:clikt-multiplatform:$cliktVersion")
    implementation("io.ktor:ktor-client:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
        apiVersion = "1.3"
        freeCompilerArgs += "-Xopt-in=com.github.ajalt.clikt.completion.ExperimentalCompletionCandidates"
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "6.3"
    distributionType = Wrapper.DistributionType.ALL
}
